package com.agro.crm.features.tasks;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST')")
    public List<Task> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST','OPERATOR')")
    public List<Task> getMyTasks() {
        return taskService.getMyTasks();
    }

    @GetMapping("/field/{fieldId}")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST')")
    public List<Task> getByField(@PathVariable Long fieldId) {
        return taskService.getByField(fieldId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST')")
    public Task create(@Valid @RequestBody TaskDto dto) {
        return taskService.create(dto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST','OPERATOR')")
    public Task changeStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {
        return taskService.changeStatus(id, status);
    }

    @PostMapping("/{id}/comments")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST','OPERATOR')")
    public TaskComment addComment(
            @PathVariable Long id,
            @RequestBody String text) {

        return taskService.addComment(id, text);
    }
}