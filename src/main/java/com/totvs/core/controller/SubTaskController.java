package com.totvs.core.controller;

import com.totvs.core.dto.SubTask.CreateSubTaskRequest;
import com.totvs.core.dto.SubTask.CreateSubTaskResponse;
import com.totvs.core.dto.SubTask.UpdateSubTaskRequest;
import com.totvs.core.service.SubTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/subtask")
class SubTaskController {

    @Autowired
    SubTaskService subTaskService;

    @PostMapping()
    public ResponseEntity<CreateSubTaskResponse> createSubTask(@RequestBody @Valid CreateSubTaskRequest data) {
        CreateSubTaskResponse newSubTask = subTaskService.createSubTask(data);
        return ResponseEntity.ok(newSubTask);
    }

    @PatchMapping("/{subTaskId}")
    public ResponseEntity<CreateSubTaskResponse> updateSubTask(@PathVariable UUID subTaskId, @RequestBody @Valid UpdateSubTaskRequest data) {
        CreateSubTaskResponse subTask = subTaskService.updateTask(subTaskId, data);
        return ResponseEntity.ok(subTask);
    }



}
