package com.agro.crm.features.agromap.feild;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FieldDto {
    private Long id;
    private String name;
    private BigDecimal areaHa;
    private SoilType soilType;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long farmerId;
}
