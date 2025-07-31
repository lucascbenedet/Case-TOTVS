package com.totvs.core.dto.SubTask;

import com.totvs.core.domain.enums.TaskStatus;

import java.util.Optional;
import java.util.UUID;

public record UpdateSubTaskRequest(Optional<String> title,
                                   Optional<String> description,
                                   Optional<TaskStatus> status,
                                   Optional<UUID> task) {
}
