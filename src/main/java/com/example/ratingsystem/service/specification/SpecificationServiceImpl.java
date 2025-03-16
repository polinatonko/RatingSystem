package com.example.ratingsystem.service.specification;

import com.example.ratingsystem.domain.dtos.specification.SpecificationCriteria;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SpecificationServiceImpl<T> implements SpecificationService<T> {
    public Specification<T> getSpecification(SpecificationCriteria<T> criteria) {
        return (root, query, builder) -> {
            var column = criteria.getColumn();
            var firstValue = criteria.getFirstValue();
            var secondValue = criteria.getSecondValue();

            Path<String> expr = criteria.isJoin()
                    ? root.join(criteria.getJoinTable()).get(column)
                    : root.get(column);

            return switch (criteria.getOperation()) {
                case EQUALS -> builder.equal(root.get(column), firstValue);
                case CONTAINS -> builder.like(builder.upper(expr), ("%" + firstValue + "%").toUpperCase());
                case BETWEEN -> builder.between(expr, firstValue.toString(), secondValue.toString());
            };
        };
    }
}