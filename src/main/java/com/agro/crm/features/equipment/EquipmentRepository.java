package com.agro.crm.features.equipment;

import com.agro.crm.features.dashboard.ChartsResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {
    @EntityGraph(attributePaths = {"operator"})
    List<Equipment> findAll();

    List<Equipment> findByStatus(EquipmentStatus status);
    List<Equipment> findByOperatorId(Long operatorId);
    long countByStatus(EquipmentStatus status);

    @Query("SELECT new com.agro.crm.features.dashboard.ChartsResponseDto$EquipmentStatusChartDto(" +
            "SUM(CASE WHEN e.status = 'AVAILABLE' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN e.status = 'IN_USE' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN e.status = 'MAINTENANCE' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN e.status = 'BROKEN' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN e.status = 'RETIRED' THEN 1 ELSE 0 END)) " +
            "FROM Equipment e")
    ChartsResponseDto.EquipmentStatusChartDto getEquipmentStatusChartData();

    @Query("SELECT new com.agro.crm.features.dashboard.ChartsResponseDto$EquipmentLoadDto(" +
            "e.id, e.name, CAST(e.type AS string), e.mileage) " +
            "FROM Equipment e ORDER BY e.mileage DESC")
    List<ChartsResponseDto.EquipmentLoadDto> findEquipmentLoads();


}
