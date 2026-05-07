package com.agro.crm.features.agromap.crop;

import lombok.Data;

@Data
public class CropDto {
    private Long fieldId;
    private String name;
    private String season;
    private String plantingDate;
    private Double expectedYield;
}
