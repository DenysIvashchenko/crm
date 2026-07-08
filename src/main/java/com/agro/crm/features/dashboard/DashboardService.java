package com.agro.crm.features.dashboard;

import com.agro.crm.features.agromap.crop.CropRepository;
import com.agro.crm.features.agromap.feild.FieldRepository;
import com.agro.crm.features.dashboard.activityLog.ActivityLog;
import com.agro.crm.features.dashboard.activityLog.ActivityLogRepository;
import com.agro.crm.features.dashboard.activityLog.RecentActivitiesDto;
import com.agro.crm.features.equipment.EquipmentRepository;
import com.agro.crm.features.equipment.EquipmentStatus;
import com.agro.crm.features.farmers.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final FarmerRepository farmerRepository;
    private final EquipmentRepository equipmentRepository;
    private final ActivityLogRepository activityLogRepository;
    private final FieldRepository fieldRepository;
    private final CropRepository cropRepository;

    @Transactional(readOnly = true)
    public DashboardResponseDto getDashboardStats() {
        long totalFarmers = farmerRepository.count();
        long totalField = fieldRepository.count();
        long totalCrops = cropRepository.count();
        Double totalArea = farmerRepository.sumTotalLandAreaHa();
        long activeEquipment = equipmentRepository.countByStatus(EquipmentStatus.AVAILABLE);
        long maintenanceEquipment = equipmentRepository.countByStatus(EquipmentStatus.MAINTENANCE);
        long totalEquipment = equipmentRepository.count();

        return DashboardResponseDto.builder()
                .totalFarmers(totalFarmers)
                .totalFields(totalField)
                .totalCrops(totalCrops)
                .totalLandAreaHa(totalArea != null ? totalArea : 0.0)
                .activeEquipmentCount(activeEquipment)
                .maintenanceEquipmentCount(maintenanceEquipment)
                .totalEquipment(totalEquipment)
                .build();
    }

    @Transactional(readOnly = true)
    public List<ActivityLog> getRecentLogs() {
        var recentLogs = activityLogRepository.findByOrderByTimestampDesc(PageRequest.of(0, 5));

        return RecentActivitiesDto.builder().recentActivities(recentLogs).build().getRecentActivities();

    }

    @Transactional(readOnly = true)
    public ChartsResponseDto getChartsData() {

        var soilChart = fieldRepository.getSoilTypeChartData();
        var cropChart = cropRepository.getCropStatusChartData();
        var equipmentChar = equipmentRepository.getEquipmentStatusChartData();
        var topEquipment = equipmentRepository.findTopEquipmentLoads(PageRequest.of(0, 4));

        return new ChartsResponseDto(soilChart, cropChart, equipmentChar, topEquipment);
    }
}
