package com.agro.crm.features.agromap.crop;

import com.agro.crm.features.agromap.feild.Field;
import com.agro.crm.features.agromap.feild.FieldRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService {

    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;

    public Crop plant(Long fieldId, Crop crop) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Field not found: " + fieldId));
        crop.setField(field);
        crop.setStatus(CropStatus.PLANTED);
        return cropRepository.save(crop);
    }

    public Crop harvest(Long cropId, Double actualYield) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found: " + cropId));
        crop.setActualYield(actualYield);
        crop.setStatus(CropStatus.HARVESTED);
        return cropRepository.save(crop);
    }

    @Transactional
    public List<Crop> getSeasonReport(String season) {
        return cropRepository.findBySeason(season);
    }

    @Transactional
    public List<Crop> getByField(Long fieldId) {
        return cropRepository.findByFieldId(fieldId);
    }

    public void delete(Long id) {
        cropRepository.deleteById(id);
    }
}
