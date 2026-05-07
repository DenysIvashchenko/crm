package com.agro.crm.features.equipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentLogRepository extends JpaRepository<EquipmentLog, Long> {
    List<EquipmentLog> findByEquipmentId(Long equipmentId);
    List<EquipmentLog> findByUserId(Long userId);
}
