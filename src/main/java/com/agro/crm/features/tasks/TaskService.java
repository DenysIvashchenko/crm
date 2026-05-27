package com.agro.crm.features.tasks;

import com.agro.crm.features.agromap.feild.Field;
import com.agro.crm.features.agromap.feild.FieldRepository;
import com.agro.crm.features.equipment.Equipment;
import com.agro.crm.features.equipment.EquipmentRepository;
import com.agro.crm.features.equipment.EquipmentStatus;
import com.agro.crm.features.user.User;
import com.agro.crm.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final FieldRepository fieldRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public Task create(
            TaskDto dto) {

        String creatorEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new EntityNotFoundException("Creator not found"));

        Field field = fieldRepository.findById(dto.getFieldId())
                .orElseThrow(() -> new EntityNotFoundException("Field not found"));

        User operator = userRepository.findById(dto.getAssignedToId())
                .orElseThrow(() -> new EntityNotFoundException("Operator not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setField(field);
        task.setAssignedTo(operator);
        task.setCreatedBy(creator);
        task.setStatus(TaskStatus.TODO);

        if (dto.getEquipmentId() != null) {
            Equipment eq = equipmentRepository.findById(dto.getEquipmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Equipment not found"));

            if (eq.getStatus() == EquipmentStatus.BROKEN || eq.getStatus() == EquipmentStatus.MAINTENANCE) {
                throw new EntityNotFoundException("Equipment not evaluable: " + eq.getStatus());
            }

            task.setEquipment(eq);
            eq.setStatus(EquipmentStatus.IN_USE);
        }

        return taskRepository.save(task);
    }

    public Task changeStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public TaskComment addComment(Long taskId, String text) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));

        TaskComment comment = new TaskComment();
        comment.setTask(task);
        comment.setUser(currentUser);
        comment.setText(text);

        return taskCommentRepository.save(comment);
    }

    @Transactional()
    public List<Task> getMyTasks() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return taskRepository.findByAssignedToId(currentUser.getId());
    }

    @Transactional()
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Transactional()
    public List<Task> getByField(Long fieldId) {
        return taskRepository.findByFieldId(fieldId);
    }
}
