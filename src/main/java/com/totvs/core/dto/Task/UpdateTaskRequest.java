package com.totvs.core.dto.Task;

import com.totvs.core.domain.enums.TaskStatus;

import java.util.Optional;
import java.util.UUID;

public record UpdateTaskRequest(
        Optional<String> title,
        Optional<String> description,
        Optional<TaskStatus> status,
        Optional<UUID> user) {

}
