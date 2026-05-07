package com.agro.crm.features.agromap.feild;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFarmerId(Long farmerId);
    boolean existsByNameAndFarmerId(String name, Long farmerId);
    boolean existsByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
