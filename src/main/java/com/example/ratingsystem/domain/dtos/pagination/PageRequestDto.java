package com.example.ratingsystem.domain.dtos.pagination;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Dto for storing data about pagination in request.
 */
@Data
public class PageRequestDto {
    private int pageNo;
    private int pageSize;
    private Sort.Direction sortDirection;
    private String sortBy;

    private static final int DEFAULT_PAGE_NO = 0;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;
    private static final String DEFAULT_SORT_BY = "id";

    public PageRequestDto(Integer pageNo, Integer pageSize, String sortDirection, String sortBy) {
        this.pageNo = (pageNo != null) ? pageNo - 1 : DEFAULT_PAGE_NO;
        this.pageSize = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
        this.sortDirection = Sort.Direction.fromOptionalString(sortDirection).orElse(DEFAULT_SORT_DIRECTION);
        this.sortBy = (sortBy != null) ? sortBy : DEFAULT_SORT_BY;
    }

    public Pageable getPageable() {
        return PageRequest.of(pageNo, pageSize, sortDirection, sortBy);
    }
}