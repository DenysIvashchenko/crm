package com.agro.crm.features.farmers;

import com.agro.crm.features.agromap.feild.FieldDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class FarmerCreateRequest {
    @NotBlank
    private String fullName;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
    @NotBlank
    private String region;
    @NotBlank
    private Double totalLandHa;
    private List<FieldDto> fields;
}
