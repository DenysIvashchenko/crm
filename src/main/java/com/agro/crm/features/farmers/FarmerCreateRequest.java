package com.agro.crm.features.farmers;

import com.agro.crm.features.agromap.feild.FieldDto;
import lombok.Data;

import java.util.List;

@Data
public class FarmerCreateRequest {
    private String fullName;
    private String phone;
    private String region;
    private Double totalLandHa;
    private List<FieldDto> fields;
}
