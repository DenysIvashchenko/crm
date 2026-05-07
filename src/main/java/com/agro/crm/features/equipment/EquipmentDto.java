package com.agro.crm.features.equipment;

import lombok.Data;

@Data
public class EquipmentDto {
    private Long id;
    private String name;
    private EquipmentType type;
    private String licensePlate;
    private EquipmentStatus status;
    private Long operatorId;
}