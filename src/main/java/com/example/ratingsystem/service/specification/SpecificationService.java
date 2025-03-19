package com.example.ratingsystem.service.specification;

import com.example.ratingsystem.domain.dtos.specification.SpecificationCriteria;
import org.springframework.data.jpa.domain.Specification;

/**
 * Service that allows to create {@link Specification} objects for the Criteria API.
 */
public interface SpecificationService<T> {
    /**
     * Creates {@link Specification} object.
     *
     * @param criteria {@link SpecificationCriteria} with necessary data
     * @return created {@link Specification}
     */
    Specification<T> getSpecification(SpecificationCriteria<T> criteria);
}
