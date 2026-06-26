package com.agro.crm.features.dashboard;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DashboardResponseDto {
    private long totalFarmers;
    private double totalLandAreaHa;
    private long activeEquipmentCount;
    private long maintenanceEquipmentCount;
    private List<ActivityLog> recentActivities;
}
