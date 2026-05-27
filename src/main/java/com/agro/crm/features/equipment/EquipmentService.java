package com.agro.crm.features.equipment;

import com.agro.crm.features.equipment.dto.EquipmentDto;
import com.agro.crm.features.equipment.dto.EquipmentResponseDto;
import com.agro.crm.features.user.User;
import com.agro.crm.features.user.UserRepository;
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
    private final UserRepository userRepository;

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
        Equipment equipment = new Equipment();
        equipment.setName(dto.getName());
        equipment.setType(dto.getType());
        equipment.setLicensePlate(dto.getLicensePlate());
        equipment.setYearMade(dto.getYearMade());
        equipment.setMileage(dto.getMileage() != null ? dto.getMileage() : 0.0);
        equipment.setNextService(dto.getNextService());
        equipment.setDeviceId(dto.getDeviceId());
        equipment.setStatus(EquipmentStatus.AVAILABLE);

        if (dto.getOperatorId() != null) {
            User operator = userRepository.findById(dto.getOperatorId())
                    .orElseThrow(() -> new RuntimeException("Operator not found ID: " + dto.getOperatorId()));
            equipment.setOperator(operator);
        }

        Equipment savedEquipment = equipmentRepository.save(equipment);
        return EquipmentResponseDto.fromEntity(savedEquipment);
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
