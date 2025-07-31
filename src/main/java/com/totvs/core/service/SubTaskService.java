package com.totvs.core.service;

import com.totvs.core.domain.SubTask.SubTask;
import com.totvs.core.domain.Task.Task;
import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.SubTask.CreateSubTaskRequest;
import com.totvs.core.dto.SubTask.CreateSubTaskResponse;
import com.totvs.core.dto.SubTask.ListSubTaskResponse;
import com.totvs.core.dto.SubTask.UpdateSubTaskRequest;
import com.totvs.core.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.totvs.core.repository.SubTaskRepository;

@Service
public class SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;
    @Autowired
    private TaskRepository taskRepository;

    public List<ListSubTaskResponse> getSubTasksByTaskId(UUID taskId) {
        List<SubTask> subTasks = subTaskRepository.findByTask_Id(taskId);
        return subTasks.stream().map(subTask -> new ListSubTaskResponse(
                subTask.getId(),
                subTask.getTitle(),
                subTask.getDescription(),
                subTask.getCreatedAt(),
                subTask.getEndedAt(),
                subTask.getStatus(),
                subTask.getTask().getId()
        )).toList();

    }

    @Transactional
    public CreateSubTaskResponse createSubTask(CreateSubTaskRequest data) {
        Task task =  taskRepository.findById(data.task()).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        SubTask subTask = new SubTask();

        subTask.setTitle(data.title());
        subTask.setDescription(data.description());
        subTask.setTask(task);
        SubTask saved = subTaskRepository.save(subTask);
        return new CreateSubTaskResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCreatedAt(),
                saved.getEndedAt(),
                saved.getStatus(),
                saved.getTask().getId());
    }

    @Transactional
    public CreateSubTaskResponse updateTask(UUID id, UpdateSubTaskRequest data) {
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
        System.out.println(data.status());
        data.status().ifPresent(status -> {
            if (!status.equals(subTask.getStatus()) && status == TaskStatus.COMPLETED) {
                subTask.setEndedAt(LocalDateTime.now());
            }
            subTask.setStatus(status);
        });

        SubTask updatedSubTask = subTaskRepository.save(subTask);
        return new CreateSubTaskResponse(
                updatedSubTask.getId(),
                updatedSubTask.getTitle(),
                updatedSubTask.getDescription(),
                updatedSubTask.getCreatedAt(),
                updatedSubTask.getEndedAt(),
                updatedSubTask.getStatus(),
                updatedSubTask.getTask().getId()

        );

    }
}