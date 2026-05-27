package com.agro.crm.features.agromap.feild;

import com.agro.crm.features.farmers.Farmer;
import com.agro.crm.features.farmers.FarmerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FarmerRepository farmerRepository;

    @Transactional
    public Field createField(FieldDto dto) {
        if (fieldRepository.existsByNameAndFarmerId(dto.getName(), dto.getFarmerId())) {
            throw new EntityNotFoundException("Field with name '" + dto.getName() + "' already exists for this farmer");
        }

        if (fieldRepository.existsByLatitudeAndLongitude(dto.getLatitude(), dto.getLongitude())) {
            throw new EntityNotFoundException("Field with these coordinates already exists");
        }
        Farmer farmer = farmerRepository.findById(dto.getFarmerId())
                .orElseThrow(() -> new EntityNotFoundException("Farmer not found"));

        Field field = new Field();
        field.setName(dto.getName());
        field.setAreaHa(dto.getAreaHa());
        field.setSoilType(dto.getSoilType());
        field.setLatitude(dto.getLatitude());
        field.setLongitude(dto.getLongitude());
        field.setBoundaryCoordinates(dto.getBoundaryCoordinates());
        field.setFarmer(farmer);

        return fieldRepository.save(field);
    }

    public List<Field> getFieldsByFarmer(Long farmerId) {
        return fieldRepository.findByFarmerId(farmerId);
    }

    public Field getById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field not found"));
    }

    @Transactional
    public Field updateField(Long id, FieldDto dto) {
        Field field = getById(id);
        field.setName(dto.getName());
        field.setAreaHa(dto.getAreaHa());
        field.setSoilType(dto.getSoilType());
        field.setLatitude(dto.getLatitude());
        field.setLongitude(dto.getLongitude());
        return fieldRepository.save(field);
    }

    public void deleteField(Long fieldId) {
        fieldRepository.deleteById(fieldId);
    }
}
