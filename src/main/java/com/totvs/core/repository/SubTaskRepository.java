package com.totvs.core.repository;

import com.totvs.core.domain.SubTask.SubTask;
import com.totvs.core.domain.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, UUID> {
    long countByTask_IdAndStatusNot(UUID taskId, TaskStatus status);

    //@EntityGraph(attributePaths = {"task", "task.user"})
    Page<SubTask> findByTask_Id(UUID taskId, Pageable pageable);
}
