package com.agro.crm.features.equipment;

import com.agro.crm.features.dashboard.activityLog.AuditAction;
import com.agro.crm.features.dashboard.activityLog.AuditService;
import com.agro.crm.features.equipment.dto.EquipmentDto;
import com.agro.crm.features.equipment.dto.EquipmentResponseDto;
import com.agro.crm.features.user.User;
import com.agro.crm.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentLogRepository equipmentLogRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    @Transactional()
    public List<EquipmentResponseDto> getAll() {
        return equipmentRepository.findAll().stream()
                .map(EquipmentResponseDto::fromEntity)
                .toList();
    }

    @Transactional()
    public List<EquipmentResponseDto> getAvailable() {
        return equipmentRepository.findByStatus(EquipmentStatus.AVAILABLE).stream()
                .map(EquipmentResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public EquipmentResponseDto create(EquipmentDto dto) {
        Equipment equipment = Equipment.create(
                dto.getName(),
                dto.getType(),
                dto.getLicensePlate(),
                dto.getYearMade(),
                dto.getMileage() != null ? dto.getMileage() : 0.0,
                dto.getNextService(),
                dto.getDeviceId()

        );

        if (dto.getOperatorId() != null) {
            User operator = userRepository.findById(dto.getOperatorId())
                    .orElseThrow(() -> new RuntimeException("Operator not found ID: " + dto.getOperatorId()));
            equipment.assignOperator(operator);
        }

        Equipment savedEquipment = equipmentRepository.save(equipment);

        auditService.logAction(
                AuditAction.EQUIPMENT_CREATED,
                "Add new Equipment " + equipment.getName()
        );

        return EquipmentResponseDto.fromEntity(savedEquipment);
    }

    public Equipment changeStatus(Long id, EquipmentStatus status) {
        Equipment eq = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found"));
        eq.updateStatus(status);

        auditService.logAction(
                AuditAction.EQUIPMENT_UPDATED,
                "Status changed"
        );
        return equipmentRepository.save(eq);
    }

    public EquipmentLog addLog(Long equipmentId, EquipmentLog log) {
        Equipment eq = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found"));

        log.setEquipment(eq);

        eq.updateMileage((eq.getMileage() == null ? 0 : eq.getMileage()) + log.getMileageAdded());
        auditService.logAction(
                AuditAction.EQUIPMENT_UPDATED,
                "Added Log"
        );
        equipmentRepository.save(eq);

        return equipmentLogRepository.save(log);
    }
}
