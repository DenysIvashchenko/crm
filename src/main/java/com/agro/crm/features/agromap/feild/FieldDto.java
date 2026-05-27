package com.agro.crm.features.agromap.feild;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FieldDto {
    @NotBlank
    private String name;
    @NotBlank
    private BigDecimal areaHa;
    @NotBlank
    private SoilType soilType;
    @NotBlank
    private BigDecimal latitude;
    @NotBlank
    private BigDecimal longitude;
    @NotBlank
    private String boundaryCoordinates;
    @NotBlank
    private Long farmerId;
}
