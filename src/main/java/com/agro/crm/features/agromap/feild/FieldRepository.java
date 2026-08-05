package com.agro.crm.features.agromap.feild;

import com.agro.crm.features.dashboard.ChartsResponseDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFarmerId(Long farmerId);

    boolean existsByNameAndFarmerId(String name, Long farmerId);

    boolean existsByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);

    @EntityGraph(attributePaths = {"crops"})
    @Query("SELECT DISTINCT f FROM Field f")
    List<Field> findAllWithCrops();

    @Query("SELECT new com.agro.crm.features.dashboard.ChartsResponseDto$SoilTypeChartDto(" +
            "SUM(CASE WHEN f.soilType = 'BLACK_SOIL' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN f.soilType = 'CLAY' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN f.soilType = 'SANDY' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN f.soilType = 'LOAM' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN f.soilType = 'PEAT' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN f.soilType = 'SILT' THEN 1 ELSE 0 END)) " +
            "FROM Field f")
    ChartsResponseDto.SoilTypeChartDto getSoilTypeChartData();
}
