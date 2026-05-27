package com.agro.crm.features.equipment.dto;

import com.agro.crm.features.equipment.Equipment;
import com.agro.crm.features.equipment.EquipmentStatus;
import com.agro.crm.features.equipment.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentResponseDto {
    private Long id;
    private String name;
    private EquipmentType type;
    private String licensePlate;
    private EquipmentStatus status;
    private Integer yearMade;
    private Double mileage;
    private LocalDate nextService;
    private LocalDateTime createdAt;

    private Long operatorId;
    private String operatorName;
    private String operatorEmail;

    private String deviceId;

    public static EquipmentResponseDto fromEntity(Equipment equipment) {
        EquipmentResponseDto.EquipmentResponseDtoBuilder builder = EquipmentResponseDto.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .type(equipment.getType())
                .licensePlate(equipment.getLicensePlate())
                .status(equipment.getStatus())
                .yearMade(equipment.getYearMade())
                .mileage(equipment.getMileage())
                .nextService(equipment.getNextService())
                .createdAt(equipment.getCreatedAt())
                .deviceId(equipment.getDeviceId());

        if (equipment.getOperator() != null) {
            builder.operatorId(equipment.getOperator().getId())
                    .operatorName(equipment.getOperator().getFullName())
                    .operatorEmail(equipment.getOperator().getEmail());
        }

        return builder.build();
    }
}
