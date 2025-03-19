package com.example.ratingsystem.domain.dtos.specification;

import lombok.Builder;
import lombok.Data;

/**
 * Specification criteria class that contains number of static methods for creating
 * objects of various {@link FilterOperation} types.
 *
 * @param <T> type of the column to search
 */
@Data
@Builder
public class SpecificationCriteria<T> {
    private String joinTable;
    private String column;
    private T firstValue;
    private T secondValue;
    private FilterOperation operation;

    /**
     * Returns true if criteria contains join table condition.
     *
     * @return {@code true} if criteria contains join table condition.
     */
    public boolean isJoin() {
        return joinTable != null && !joinTable.isEmpty();
    }

    /**
     * Creates {@link SpecificationCriteria} for between condition without join.
     *
     * @param column name of the column
     * @param firstValue low value to compare with
     * @param secondValue high value to compare with
     * @param <T> type of the column
     * @return {@link SpecificationCriteria} for between condition
     */
    public static <T> SpecificationCriteria<T> betweenCriteria(String column, T firstValue, T secondValue) {
        return SpecificationCriteria.<T>builder()
                .column(column)
                .firstValue(firstValue)
                .secondValue(secondValue)
                .operation(FilterOperation.BETWEEN)
                .build();
    }

    /**
     * Creates {@link SpecificationCriteria} for between condition with join.
     *
     * @param joinTable name of the join table
     * @param column name of the column
     * @param firstValue low value to compare with
     * @param secondValue high value to compare with
     * @param <T> type of the column
     * @return {@link SpecificationCriteria} for between condition
     */
    public static <T> SpecificationCriteria<T> betweenCriteria(String joinTable, String column, T firstValue, T secondValue) {
        var criteria = betweenCriteria(column, firstValue, secondValue);
        criteria.setJoinTable(joinTable);
        return criteria;
    }

    /**
     * Creates {@link SpecificationCriteria} for equals condition without join.
     *
     * @param column name of the column
     * @param value value to compare with
     * @param <T> type of the column
     * @return {@link SpecificationCriteria} for equals condition
     */
    public static <T> SpecificationCriteria<T> equalsCriteria(String column, T value) {
        return SpecificationCriteria.<T>builder()
                .column(column)
                .firstValue(value)
                .operation(FilterOperation.EQUALS)
                .build();
    }

    /**
     * Creates {@link SpecificationCriteria} for equals condition with join.
     *
     * @param joinTable name of the join table
     * @param column name of the column
     * @param value value to compare with
     * @param <T> type of the column
     * @return {@link SpecificationCriteria} for equals condition
     */
    public static <T> SpecificationCriteria<T> equalsCriteria(String joinTable, String column, T value) {
        var criteria = equalsCriteria(column, value);
        criteria.setJoinTable(joinTable);
        return criteria;
    }

    /**
     * Creates {@link SpecificationCriteria} for contains condition without join.
     *
     * @param column name of the column
     * @param value value to search
     * @param <T> type of the column
     * @return {@link SpecificationCriteria} for contains condition
     */
    public static <T> SpecificationCriteria<T> containsCriteria(String column, T value) {
        return SpecificationCriteria.<T>builder()
                .column(column)
                .firstValue(value)
                .operation(FilterOperation.CONTAINS)
                .build();
    }

    /**
     * Creates {@link SpecificationCriteria} for contains condition with join.
     *
     * @param joinTable name of the join table
     * @param column name of the column
     * @param value value to search
     * @param <T> type of the column
     * @return {@link SpecificationCriteria} for contains condition
     */
    public static <T> SpecificationCriteria<T> containsCriteria(String joinTable, String column, T value) {
        var criteria = containsCriteria(column, value);
        criteria.setJoinTable(joinTable);
        return criteria;
    }

    /**
     * Represents specification's conditions.
     */
    public enum FilterOperation {
        EQUALS,
        CONTAINS,
        BETWEEN
    }
}