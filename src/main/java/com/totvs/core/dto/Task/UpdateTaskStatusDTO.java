package com.totvs.core.dto.Task;

import com.totvs.core.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusDTO(
        @NotNull(message = "Status is required")
        TaskStatus status
) {
}
