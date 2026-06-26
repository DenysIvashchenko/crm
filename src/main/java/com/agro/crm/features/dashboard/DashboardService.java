package com.agro.crm.features.dashboard;

import com.agro.crm.features.equipment.EquipmentRepository;
import com.agro.crm.features.equipment.EquipmentStatus;
import com.agro.crm.features.farmers.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final FarmerRepository farmerRepository;
    private final EquipmentRepository equipmentRepository;
    private final ActivityLogRepository activityLogRepository;

    @Transactional(readOnly = true)
    public DashboardResponseDto getDashboardStats() {
        long totalFarmers = farmerRepository.count();
        Double totalArea = farmerRepository.sumTotalLandAreaHa();
        long activeEquipment = equipmentRepository.countByStatus(EquipmentStatus.AVAILABLE);
        long maintenanceEquipment = equipmentRepository.countByStatus(EquipmentStatus.MAINTENANCE);

        var recentLogs = activityLogRepository.findByOrderByTimestampDesc(PageRequest.of(0, 5));

        return DashboardResponseDto.builder()
                .totalFarmers(totalFarmers)
                .totalLandAreaHa(totalArea != null ? totalArea : 0.0)
                .activeEquipmentCount(activeEquipment)
                .maintenanceEquipmentCount(maintenanceEquipment)
                .recentActivities(recentLogs)
                .build();
    }
}
