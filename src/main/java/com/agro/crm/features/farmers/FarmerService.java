package com.agro.crm.features.farmers;

import com.agro.crm.features.agromap.feild.Field;
import com.agro.crm.features.user.User;
import com.agro.crm.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;

    private final String[] colors = {"#2E7D32","#1565C0","#E65100","#6A1B9A","#E65100","#AD1457","#00695C","#4E342E","#4E342E"};

    @Transactional
    @CacheEvict(value = "farmers", allEntries = true)
    public Farmer createFarmer(FarmerCreateRequest dto) {
        Random rand = new Random();
        int randomNum = rand.nextInt(8);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Manager not found"));
        Farmer farmer = new Farmer();
        farmer.setFullName(dto.getFullName());
        farmer.setPhone(dto.getPhone());
        farmer.setEmail(dto.getEmail());
        farmer.setRegion(dto.getRegion());
        farmer.setTotalLandHa(dto.getTotalLandHa());
        farmer.setColor(colors[randomNum]);
        farmer.setStatus(FarmerStatus.NEW);
        farmer.setManager(manager);

        if (dto.getFields() != null && !dto.getFields().isEmpty()) {
            List<Field> fieldEntities = dto.getFields().stream().map(fieldDto -> {
                Field field = new Field();
                field.setName(fieldDto.getName());
                field.setAreaHa(fieldDto.getAreaHa());
                field.setSoilType(fieldDto.getSoilType());
                field.setLatitude(fieldDto.getLatitude());
                field.setColorField(colors[randomNum]);
                field.setBoundaryCoordinates(fieldDto.getBoundaryCoordinates());
                field.setLongitude(fieldDto.getLongitude());

                field.setFarmer(farmer);
                return field;
            }).toList();

            farmer.setFields(fieldEntities);
        }

        return farmerRepository.save(farmer);
    }

    @Cacheable(value = "farmers")
    public List<Farmer> getAll() {
        return farmerRepository.findAllWithFieldsAndManager();
    }

    public Farmer getById(Long id) {
        return farmerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farmer not found"));
    }

    @Transactional
    public Farmer update(Long id, FarmerCreateRequest dto) {
        Farmer farmer = getById(id);
        farmer.setFullName(dto.getFullName());
        farmer.setPhone(dto.getPhone());
        farmer.setRegion(dto.getRegion());
        farmer.setStatus(dto.getStatus());
        farmer.setTotalLandHa(dto.getTotalLandHa());

        return farmerRepository.save(farmer);
    }

    public void delete(Long id) {
        farmerRepository.deleteById(id);
    }
}
