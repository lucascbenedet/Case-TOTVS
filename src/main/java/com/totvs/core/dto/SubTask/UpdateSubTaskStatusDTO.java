package com.totvs.core.dto.SubTask;

import com.totvs.core.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateSubTaskStatusDTO(
        @NotNull(message = "Status is required")
        TaskStatus status
) {
}
