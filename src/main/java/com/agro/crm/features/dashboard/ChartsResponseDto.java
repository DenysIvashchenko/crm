package com.agro.crm.features.dashboard;

import java.util.List;

public record ChartsResponseDto(
        SoilTypeChartDto soilTypeChart,
        CropStatusChartDto cropStatusChart,
        EquipmentStatusChartDto equipmentStatusChar,
        List<EquipmentLoadDto> equipmentLoads
) {
    public record SoilTypeChartDto(
            Long blackSoilCount,
            Long clayCount,
            Long sandyCount,
            Long loamCount,
            Long peatCount,
            Long siltCount
    ) {}

    public record CropStatusChartDto(
            Long growingCount,
            Long harvestedCount,
            Long plantedCount,
            Long failedCount
    ) {}

    public record EquipmentStatusChartDto(
            Long availableCount,
            Long inUseCount,
            Long maintenanceCount,
            Long brokenCount,
            Long retiredCount
    ) {}

    public record EquipmentLoadDto(
            Long id,
            String name,
            String  type,
            Double loadPercent
    ) {}
}
