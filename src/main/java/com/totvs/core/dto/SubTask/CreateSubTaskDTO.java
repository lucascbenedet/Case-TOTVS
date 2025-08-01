package com.totvs.core.dto.SubTask;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateSubTaskDTO(@NotBlank(message = "Title is required") @NotNull(message = "Title is required")  String title,
                               String description,
                               @NotNull(message = "Task id is required") UUID task) {
}
