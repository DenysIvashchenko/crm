package com.agro.crm.features.tasks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(Long userId);

    List<Task> findByFieldId(Long fieldId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedToIdAndStatus(Long userId, TaskStatus status);
}
