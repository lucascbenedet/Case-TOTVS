package com.totvs.core.controller;

import com.totvs.core.dto.SubTask.CreateSubTaskDTO;
import com.totvs.core.dto.SubTask.SubTaskResponseDTO;
import com.totvs.core.dto.SubTask.UpdateSubTaskStatusDTO;
import com.totvs.core.service.SubTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/subtask")
@Tag(name = "SubTask")
class SubTaskController {

    @Autowired
    SubTaskService subTaskService;

    @Operation(
            summary = "Cria uma nova sub-task"
    )
    @PostMapping()
    public ResponseEntity<SubTaskResponseDTO> createSubTask(@RequestBody @Valid CreateSubTaskDTO data) {
        SubTaskResponseDTO newSubTask = subTaskService.createSubTask(data);
        return ResponseEntity.ok(newSubTask);
    }

    @Operation(
            summary = "Atualiza status de uma sub-task"
    )
    @PatchMapping("/{subTaskId}/status")
    public ResponseEntity<SubTaskResponseDTO> updateSubTaskStatus(@PathVariable UUID subTaskId, @RequestBody @Valid UpdateSubTaskStatusDTO data) {
        SubTaskResponseDTO subTask = subTaskService.updateSubTaskStatus(subTaskId, data.status());
        return ResponseEntity.ok(subTask);
    }



}
