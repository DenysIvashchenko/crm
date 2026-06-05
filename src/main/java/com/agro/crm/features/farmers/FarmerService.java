package com.agro.crm.features.farmers;

import com.agro.crm.features.agromap.feild.Field;
import com.agro.crm.features.user.User;
import com.agro.crm.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;
    private final FarmerCacheService farmerCacheService;

    private final String[] colors = {"#2E7D32", "#1565C0", "#E65100", "#6A1B9A", "#E65100", "#AD1457", "#00695C", "#4E342E", "#4E342E"};

    @Transactional
    @CacheEvict(value = "farmers", allEntries = true)
    public Farmer createFarmer(FarmerCreateRequest dto) {
        Random rand = new Random();
        int randomNum = rand.nextInt(8);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User manager = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Manager not found"));

        Farmer farmer = Farmer.create(dto, manager, colors[randomNum]);

        if (dto.getFields() != null) {

            dto.getFields().forEach(fieldDto -> {
                Field field = Field.create(fieldDto, colors[randomNum]);
                farmer.addField(field);

            });
        }

        return farmerRepository.save(farmer);
    }

    public List<Farmer> getAll(String search) {
        String cleanSearch = (search != null && !search.trim().isEmpty()) ? search.trim() : null;

        if (cleanSearch == null) {
            return farmerCacheService.getAllWithCache();
        }
        return farmerRepository.searchFarmersWithFieldsAndManager(search);
    }

    public Farmer getById(Long id) {
        return farmerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Farmer not found"));
    }

    @Transactional
    @CacheEvict(value = "farmers", allEntries = true)
    public Farmer update(Long id, FarmerCreateRequest dto) {
        Farmer farmer = getById(id);

        farmer.updateProfile(dto.getFullName(), dto.getPhone(), dto.getRegion(), dto.getTotalLandHa(), dto.getStatus());
        return farmer;
    }

    @CacheEvict(value = "farmers", allEntries = true)
    public void delete(Long id) {
        farmerRepository.deleteById(id);
    }
}
