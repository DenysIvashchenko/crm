package com.agro.crm.features.agromap.feild;

import com.agro.crm.features.dashboard.activityLog.AuditAction;
import com.agro.crm.features.dashboard.activityLog.AuditService;
import com.agro.crm.features.farmers.Farmer;
import com.agro.crm.features.farmers.FarmerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FarmerRepository farmerRepository;
    private final AuditService auditService;

    @Transactional
    @CacheEvict(value = "field", allEntries = true)
    public Field createField(FieldDto dto) {
        if (fieldRepository.existsByNameAndFarmerId(dto.getName(), dto.getFarmerId())) {
            throw new EntityNotFoundException("Field with name '" + dto.getName() + "' already exists");
        }

        if (fieldRepository.existsByLatitudeAndLongitude(dto.getLatitude(), dto.getLongitude())) {
            throw new EntityNotFoundException("Field with these coordinates already exists");
        }

        Farmer farmer = farmerRepository.findById(dto.getFarmerId()).orElseThrow(() -> new EntityNotFoundException("Farmer not found"));
        Field field = Field.create(dto, farmer.getColor());
        farmer.addField(field);

        auditService.logAction(
                AuditAction.FIELD_CREATED,
                "create Field"
        );

        return fieldRepository.save(field);
    }

    public List<Field> getFieldsByFarmer(Long farmerId) {
        return fieldRepository.findByFarmerId(farmerId);
    }

    public Field getById(Long id) {
        return fieldRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Field not found"));
    }

    @Cacheable("field")
    public List<Field> getAllFields() {
        return fieldRepository.findAllWithCrops();
    }

    @Transactional
    @CacheEvict(value = "field", allEntries = true, beforeInvocation = false)
    public Field updateField(Long id, FieldDto dto) {
        Field field = getById(id);

        field.updateInfo(
                dto.getName(),
                dto.getAreaHa(),
                dto.getSoilType(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getBoundaryCoordinates()
        );

        auditService.logAction(
                AuditAction.FIELD_BOUNDARIES_UPDATED,
                " updateField"
        );

        return field;
    }

    @CacheEvict(value = "field", allEntries = true)
    public void deleteField(Long fieldId) {
        auditService.logAction(
                AuditAction.FIELD_DELETED,
                "delete Field"
        );

        fieldRepository.deleteById(fieldId);
    }
}
