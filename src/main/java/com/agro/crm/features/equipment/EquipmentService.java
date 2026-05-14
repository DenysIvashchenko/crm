package com.agro.crm.features.equipment;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentLogRepository equipmentLogRepository;

    @Transactional()
    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    @Transactional()
    public List<Equipment> getAvailable() {
        return equipmentRepository.findByStatus(EquipmentStatus.AVAILABLE);
    }

    public Equipment create(Equipment equipment) {
        equipment.setStatus(EquipmentStatus.AVAILABLE);
        return equipmentRepository.save(equipment);
    }

    public Equipment changeStatus(Long id, EquipmentStatus status) {
        Equipment eq = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found"));
        eq.setStatus(status);
        return equipmentRepository.save(eq);
    }

    public EquipmentLog addLog(Long equipmentId, EquipmentLog log) {
        Equipment eq = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found"));

        log.setEquipment(eq);

        eq.setMileage((eq.getMileage() == null ? 0 : eq.getMileage()) + log.getMileageAdded());
        equipmentRepository.save(eq);

        return equipmentLogRepository.save(log);
    }
}
