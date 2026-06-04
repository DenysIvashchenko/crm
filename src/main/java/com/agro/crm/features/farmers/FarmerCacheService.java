package com.agro.crm.features.farmers;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class FarmerCacheService {
   private final FarmerRepository repository;

    @Cacheable("farmers")
    public List<Farmer> getAllWithCache() {
        return repository.findAllWithFieldsAndManager();
    }
}
