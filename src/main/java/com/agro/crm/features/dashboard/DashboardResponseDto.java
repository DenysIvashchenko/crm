package com.agro.crm.features.dashboard;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DashboardResponseDto {
    private long totalFarmers;
    private long totalFields;
    private long totalCrops;
    private double totalLandAreaHa;
    private long activeEquipmentCount;
    private long maintenanceEquipmentCount;
    private long totalEquipment;
}
