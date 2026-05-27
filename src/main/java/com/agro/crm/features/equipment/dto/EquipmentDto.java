package com.agro.crm.features.equipment.dto;

import com.agro.crm.features.equipment.EquipmentType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {
    @NotBlank
    private String name;

    @NotBlank
    private EquipmentType type;

    private String licensePlate;
    private Integer yearMade;
    private Double mileage;
    private LocalDate nextService;
    private Long operatorId;
    private String deviceId;
}
