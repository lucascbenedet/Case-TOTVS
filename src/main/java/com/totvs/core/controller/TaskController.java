package com.totvs.core.controller;

import com.totvs.core.domain.SubTask.SubTask;
import com.totvs.core.domain.Task.Task;
import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.SubTask.ListSubTaskResponse;
import com.totvs.core.dto.Task.*;
import com.totvs.core.service.SubTaskService;
import com.totvs.core.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
class TaskController {

    @Autowired
    TaskService taskService;
    @Autowired
    SubTaskService subTaskService;

    @PostMapping()
    public ResponseEntity<CreateTaskResponse> createTask(@RequestBody @Valid CreateTaskRequest data) {
        CreateTaskResponse newTask =  taskService.createTask(data);
        return ResponseEntity.ok(newTask);

    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<CreateTaskResponse> updateTask(@PathVariable UUID taskId, @RequestBody @Valid UpdateTaskRequest data) {
        CreateTaskResponse task = taskService.updateTask(taskId, data);
        return ResponseEntity.ok(task);
    }

    @GetMapping()
    public ResponseEntity<List<ListTasksResponse>> getTasks(@RequestParam(required = false) UUID userId,
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

        return ResponseEntity.ok(taskService.listFilteredTasks(filter));
    }
    @GetMapping("/{taskId}/subtasks")
    public ResponseEntity<List<ListSubTaskResponse>> getSubTasksByTaskId(@PathVariable UUID taskId) {
        return ResponseEntity.ok(subTaskService.getSubTasksByTaskId(taskId));
    }
}
