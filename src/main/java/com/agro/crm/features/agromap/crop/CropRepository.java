package com.agro.crm.features.agromap.crop;

import com.agro.crm.features.dashboard.ChartsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByFieldId(Long fieldId);
    List<Crop> findBySeason(String season);
    List<Crop> findByStatus(CropStatus status);
    List<Crop> findByFieldIdAndSeason(Long fieldId, String season);

    @Query("SELECT new com.agro.crm.features.dashboard.ChartsResponseDto$CropStatusChartDto(" +
            "SUM(CASE WHEN c.status = 'GROWING' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.status = 'HARVESTED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.status = 'PLANTED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.status = 'FAILED' THEN 1 ELSE 0 END)) " +
            "FROM Crop c")
    ChartsResponseDto.CropStatusChartDto getCropStatusChartData();
}
