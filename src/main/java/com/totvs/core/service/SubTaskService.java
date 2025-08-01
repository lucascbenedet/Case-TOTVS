package com.totvs.core.service;

import com.totvs.core.domain.SubTask.SubTask;
import com.totvs.core.domain.Task.Task;
import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.SubTask.CreateSubTaskDTO;
import com.totvs.core.dto.SubTask.SubTaskResponseDTO;
import com.totvs.core.dto.SubTask.UpdateSubTaskDTO;
import com.totvs.core.mappers.SubTaskMapper;
import com.totvs.core.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import com.totvs.core.repository.SubTaskRepository;

@Service
public class SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskMapper subTaskMapper;

    public Page<SubTaskResponseDTO> getSubTasksByTaskId(UUID taskId, Pageable pageable) {
        Page<SubTask> subTasks = subTaskRepository.findByTask_Id(taskId, pageable);
        return subTasks.map(subTask ->  subTaskMapper.toSubTaskResponseDTO(subTask));

    }

    @Transactional
    public SubTaskResponseDTO createSubTask(CreateSubTaskDTO data) {
        Task task =  taskRepository.findById(data.task()).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        SubTask subTask = new SubTask();

        subTask.setTitle(data.title());
        subTask.setDescription(data.description());
        subTask.setTask(task);
        SubTask saved = subTaskRepository.save(subTask);
        return subTaskMapper.toSubTaskResponseDTO(saved);
    }

    @Transactional
    public SubTaskResponseDTO updateSubTask(UUID id, UpdateSubTaskDTO data) {
        SubTask subTask = subTaskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subtask not found"));

        data.title().ifPresent(subTask::setTitle);
        data.description().ifPresent(subTask::setDescription);
        data.task().ifPresent(uid ->
        {
            if (!uid.equals(subTask.getTask().getId())) {
                Task newTask = taskRepository.findById(uid).orElseThrow(() -> new EntityNotFoundException("Task not found"));
                subTask.setTask(newTask);
            }

        });
        data.status().ifPresent(status -> {
            if (!status.equals(subTask.getStatus()) && status == TaskStatus.COMPLETED) {
                subTask.setEndedAt(LocalDateTime.now());
            }
            subTask.setStatus(status);
        });

        SubTask updatedSubTask = subTaskRepository.save(subTask);
        return subTaskMapper.toSubTaskResponseDTO(updatedSubTask);

    }

    @Transactional
    public SubTaskResponseDTO updateSubTaskStatus(UUID id, TaskStatus status) {
        UpdateSubTaskDTO subTaskDTO = new UpdateSubTaskDTO(
                Optional.empty(),
                Optional.empty(),
                Optional.of(status),
                Optional.empty()
                );
        return this.updateSubTask(id, subTaskDTO);
    }
}