package com.agro.crm.features.contract;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ContractResponse {
    private Long id;
    private String title;
    private String contractNumber;
    private String counterpartyName;
    private LocalDate contractDate;
    private LocalDate expiryDate;
    private BigDecimal amount;
    private String status;
    private String fileName;
    private boolean hasFile;
    private LocalDateTime createdAt;

    public static ContractResponse from(Contract contract) {
        return new ContractResponse(
                contract.getId(),
                contract.getTitle(),
                contract.getContractNumber(),
                contract.getCounterpartyName(),
                contract.getContractDate(),
                contract.getExpiryDate(),
                contract.getAmount(),
                contract.getStatus().name(),
                contract.getFileName(),
                contract.hasFile(),
                contract.getCreatedAt()
        );
    }
}
