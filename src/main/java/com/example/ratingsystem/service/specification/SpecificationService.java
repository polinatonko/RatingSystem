package com.example.ratingsystem.service.specification;

import com.example.ratingsystem.domain.dtos.specification.SpecificationCriteria;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationService<T> {
    Specification<T> getSpecification(SpecificationCriteria<T> criteria);
}
