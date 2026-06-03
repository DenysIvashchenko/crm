package com.agro.crm.features.agromap.feild;

import com.agro.crm.features.farmers.Farmer;
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
}
