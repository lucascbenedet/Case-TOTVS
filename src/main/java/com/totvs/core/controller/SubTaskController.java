package com.totvs.core.controller;

import com.totvs.core.dto.SubTask.CreateSubTaskDTO;
import com.totvs.core.dto.SubTask.SubTaskResponseDTO;
import com.totvs.core.dto.SubTask.UpdateSubTaskStatusDTO;
import com.totvs.core.service.SubTaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/subtask")
@Tag(name = "SubTask Endpoints")
class SubTaskController {

    @Autowired
    SubTaskService subTaskService;

    @PostMapping()
    public ResponseEntity<SubTaskResponseDTO> createSubTask(@RequestBody @Valid CreateSubTaskDTO data) {
        SubTaskResponseDTO newSubTask = subTaskService.createSubTask(data);
        return ResponseEntity.ok(newSubTask);
    }

    @PatchMapping("/{subTaskId}/status")
    public ResponseEntity<SubTaskResponseDTO> updateSubTaskStatus(@PathVariable UUID subTaskId, @RequestBody @Valid UpdateSubTaskStatusDTO data) {
        SubTaskResponseDTO subTask = subTaskService.updateSubTaskStatus(subTaskId, data.status());
        return ResponseEntity.ok(subTask);
    }



}
