package com.example.ratingsystem.domain.enums;

/**
 * Represents status of the submit request.
 */
public enum RequestStatus {
    APPROVED,
    REJECTED,
    WAITING;

    /**
     * Finds enum value by name or returns null.
     *
     * @param name enum name
     * @return {@link RequestStatus} or {@code null}
     */
    public static RequestStatus findByName(String name) {
        for (var value: values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
