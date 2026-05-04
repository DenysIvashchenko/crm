package com.agro.crm.features.agromap.crop;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByFieldId(Long fieldId);
    List<Crop> findBySeason(String season);
    List<Crop> findByStatus(CropStatus status);
    List<Crop> findByFieldIdAndSeason(Long fieldId, String season);
}
