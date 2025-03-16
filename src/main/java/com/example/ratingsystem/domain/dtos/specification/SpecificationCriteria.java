package com.example.ratingsystem.domain.dtos.specification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecificationCriteria<T> {
    private String joinTable;
    private String column;
    private T firstValue;
    private T secondValue;
    private FilterOperation operation;

    public boolean isJoin() {
        return joinTable != null && !joinTable.isEmpty();
    }

    public static <T> SpecificationCriteria<T> betweenCriteria(String column, T firstValue, T secondValue) {
        return SpecificationCriteria.<T>builder()
                .column(column)
                .firstValue(firstValue)
                .secondValue(secondValue)
                .operation(FilterOperation.BETWEEN)
                .build();
    }

    public static <T> SpecificationCriteria<T> betweenCriteria(String joinTable, String column, T firstValue, T secondValue) {
        var criteria = betweenCriteria(column, firstValue, secondValue);
        criteria.setJoinTable(joinTable);
        return criteria;
    }

    public static <T> SpecificationCriteria<T> equalsCriteria(String column, T value) {
        return SpecificationCriteria.<T>builder()
                .column(column)
                .firstValue(value)
                .operation(FilterOperation.EQUALS)
                .build();
    }

    public static <T> SpecificationCriteria<T> equalsCriteria(String joinTable, String column, T value) {
        var criteria = equalsCriteria(column, value);
        criteria.setJoinTable(joinTable);
        return criteria;
    }

    public static <T> SpecificationCriteria<T> containsCriteria(String column, T value) {
        return SpecificationCriteria.<T>builder()
                .column(column)
                .firstValue(value)
                .operation(FilterOperation.CONTAINS)
                .build();
    }

    public static <T> SpecificationCriteria<T> containsCriteria(String joinTable, String column, T value) {
        var criteria = containsCriteria(column, value);
        criteria.setJoinTable(joinTable);
        return criteria;
    }

    public enum FilterOperation {
        EQUALS,
        CONTAINS,
        BETWEEN
    }
}