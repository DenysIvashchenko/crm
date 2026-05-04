package com.agro.crm.features.farmers;

import com.agro.crm.features.agromap.feild.Field;
import com.agro.crm.features.user.User;
import com.agro.crm.features.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;

    @Transactional
    public Farmer createFarmer(FarmerCreateRequest dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        Farmer farmer = new Farmer();
        farmer.setFullName(dto.getFullName());
        farmer.setPhone(dto.getPhone());
        farmer.setRegion(dto.getRegion());
        farmer.setTotalLandHa(dto.getTotalLandHa());
        farmer.setStatus(FarmerStatus.ACTIVE);
        farmer.setManager(manager);

        if (dto.getFields() != null && !dto.getFields().isEmpty()) {
            List<Field> fieldEntities = dto.getFields().stream().map(fieldDto -> {
                Field field = new Field();
                field.setName(fieldDto.getName());
                field.setAreaHa(fieldDto.getAreaHa());
                field.setSoilType(fieldDto.getSoilType());
                field.setLatitude(fieldDto.getLatitude());
                field.setLongitude(fieldDto.getLongitude());

                field.setFarmer(farmer);
                return field;
            }).toList();

            farmer.setFields(fieldEntities);
        }

        return farmerRepository.save(farmer);
    }

    public List<Farmer> getAll() {
        return farmerRepository.findAll();
    }

    public Farmer getById(Long id) {
        return farmerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));
    }

    @Transactional
    public Farmer update(Long id, FarmerCreateRequest dto) {
        Farmer farmer = getById(id);
        farmer.setFullName(dto.getFullName());
        farmer.setPhone(dto.getPhone());
        farmer.setRegion(dto.getRegion());
        farmer.setTotalLandHa(dto.getTotalLandHa());

        return farmerRepository.save(farmer);
    }

    public void delete(Long id) {
        farmerRepository.deleteById(id);
    }
}
