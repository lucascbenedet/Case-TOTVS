package com.totvs.core.controller;

import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.SubTask.SubTaskResponseDTO;
import com.totvs.core.dto.Task.*;
import com.totvs.core.service.SubTaskService;
import com.totvs.core.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@Tag(name = "Tasks", description = "Task endpoints")
class TaskController {

    @Autowired
    TaskService taskService;
    @Autowired
    SubTaskService subTaskService;

    @PostMapping()
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid CreateTaskDTO data) {
        TaskResponseDTO newTask =  taskService.createTask(data);
        return ResponseEntity.ok(newTask);

    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(@PathVariable UUID taskId, @RequestBody @Valid UpdateTaskStatusDTO data) {
        TaskResponseDTO task = taskService.updateTaskStatus(taskId, data.status());
        return ResponseEntity.ok(task);
    }

    @GetMapping()
    public Page<TaskResponseDTO> getTasks(@PageableDefault() Pageable pageable,
                                          @RequestParam(required = false) UUID userId,
                                          @RequestParam(required = false) TaskStatus status,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAfter,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdBefore
    ) {
        TaskFilter filter = new TaskFilter(
                status,
                userId,
                createdAfter,
                createdBefore
        );

        return taskService.listFilteredTasks(filter, pageable);
    }
    @GetMapping("/{taskId}/subtasks")
    public Page<SubTaskResponseDTO> getSubTasksByTaskId(
            @PageableDefault() Pageable pageable,
            @PathVariable UUID taskId) {
        return subTaskService.getSubTasksByTaskId(taskId, pageable);
    }
}
