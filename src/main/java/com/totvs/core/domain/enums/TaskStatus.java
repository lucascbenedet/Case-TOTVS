package com.totvs.core.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;

    @JsonCreator
    public static TaskStatus fromString(String value) {
        try {
            return TaskStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid status. Allowed values are: "
                            + Arrays.toString(TaskStatus.values())
            );
        }
    }
}

