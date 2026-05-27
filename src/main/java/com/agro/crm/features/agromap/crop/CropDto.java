package com.agro.crm.features.agromap.crop;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CropDto {
    @NotBlank
    private Long fieldId;
    @NotBlank
    private String name;
    @NotBlank
    private String season;
    @NotBlank
    private String plantingDate;
    private Double expectedYield;
}
