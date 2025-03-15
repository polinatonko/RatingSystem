package com.example.ratingsystem.domain.enums;

public enum RequestStatus {
    APPROVED,
    REJECTED,
    WAITING;

    public static RequestStatus findByName(String name) {
        for (var value: values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
