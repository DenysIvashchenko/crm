package com.agro.crm.features.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    @NotBlank(message = "name is required")
    private String title;

    private String description;

    @NotNull(message = "field is required")
    private Long fieldId;

    @NotNull(message = "operator is required")
    private Long assignedToId;

    private Long equipmentId;

    private LocalDateTime deadline;
}