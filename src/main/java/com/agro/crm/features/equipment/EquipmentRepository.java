package com.agro.crm.features.equipment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {
    @EntityGraph(attributePaths = {"operator"})
    List<Equipment> findAll();
    List<Equipment> findByStatus(EquipmentStatus status);
    List<Equipment> findByOperatorId(Long operatorId);
}
