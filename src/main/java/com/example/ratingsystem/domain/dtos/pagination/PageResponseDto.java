package com.example.ratingsystem.domain.dtos.pagination;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

/**
 * Dto for responses with pagination.
 *
 * @param <T> type of the requested data
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponseDto<T> {
    private List<T> content;
    private int pageNo;
    private int pageSize;
    private int totalPages;

    /**
     * Converts {@link Pageable} to the {@link PageResponseDto} using provided function to convert underlying data
     * into desired format.
     *
     * @param page {@link Pageable} with all necessary data
     * @param transform {@link Function} to convert data
     * @param <T> data type in {@link Pageable}
     * @param <R> desired data type in response
     * @return created {@link PageResponseDto}
     */
    public static <T, R> PageResponseDto<R> from(Page<T> page, Function<T, R> transform) {
        var pageDto = new PageResponseDto<R>();
        pageDto.setPageNo(page.getNumber() + 1);
        pageDto.setPageSize(page.getSize());
        pageDto.setTotalPages(page.getTotalPages());
        var content = page.getContent()
                .stream()
                .map(transform)
                .toList();
        pageDto.setContent(content);
        return pageDto;
    }
}