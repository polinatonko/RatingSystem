package com.example.ratingsystem.domain.dtos.pagination;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponseDto<T> {
    private List<T> content;
    private int pageNo;
    private int pageSize;
    private int totalPages;

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