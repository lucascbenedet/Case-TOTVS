package com.totvs.core.dto.Task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTaskDTO(
        @NotBlank(message = "Title is required") String title,
        String description,
        @NotNull(message = "User id is required") UUID user
)
{ }
