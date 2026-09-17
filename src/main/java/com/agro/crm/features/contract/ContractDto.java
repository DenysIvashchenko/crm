package com.agro.crm.features.contract;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContractDto {
    private String title;
    private String contractNumber;
    private String counterpartyName;
    private LocalDate contractDate;
    private LocalDate expiryDate;
    private BigDecimal amount;
}