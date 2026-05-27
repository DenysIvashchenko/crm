package com.agro.crm.features.agromap.crop;

import com.agro.crm.features.agromap.feild.Field;
import com.agro.crm.features.agromap.feild.FieldRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService {

    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;

    public Crop plant(CropDto dto) {
        Field field = fieldRepository.findById(dto.getFieldId())
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + dto.getFieldId()));

        Crop crop = new Crop();
        crop.setName(dto.getName());
        crop.setSeason(dto.getSeason());
        crop.setPlantingDate(LocalDate.parse(dto.getPlantingDate()));
        crop.setExpectedYield(BigDecimal.valueOf(dto.getExpectedYield()));
        crop.setField(field);
        crop.setStatus(CropStatus.PLANTED);

        return cropRepository.save(crop);
    }

    public Crop harvest(Long cropId, Double actualYield) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new EntityNotFoundException("Crop not found: " + cropId));
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
