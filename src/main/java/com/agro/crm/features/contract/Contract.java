package com.agro.crm.features.contract;

import com.agro.crm.features.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title; // "Договор поставки пшеницы №123"

    @Column(name = "contract_number", length = 50)
    private String contractNumber;

    @Column(name = "counterparty_name", nullable = false, length = 200)
    private String counterpartyName; // контрагент

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    // ─── Файл (подписанный PDF) ─────────────────────────────────────────
    @Column(name = "file_name")
    private String fileName; // оригинальное имя файла

    @Column(name = "file_path")
    private String filePath; // путь на диске / в S3

    @Column(name = "file_size")
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy; // менеджер, который внёс контракт

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    // ─── Constructor ──────────────────────────────────────────────────────
    private Contract(
            String title,
            String contractNumber,
            String counterpartyName,
            LocalDate contractDate,
            LocalDate expiryDate,
            BigDecimal amount,
            User createdBy
    ) {
        this.validate(title, counterpartyName);

        this.title = title;
        this.contractNumber = contractNumber;
        this.counterpartyName = counterpartyName;
        this.contractDate = contractDate;
        this.expiryDate = expiryDate;
        this.amount = amount;
        this.createdBy = createdBy;
        this.status = ContractStatus.DRAFT;
    }

    // ─── Factory method ──────────────────────────────────────────────────
    public static Contract create(ContractDto dto, User manager) {
        return new Contract(
                dto.getTitle(),
                dto.getContractNumber(),
                dto.getCounterpartyName(),
                dto.getContractDate(),
                dto.getExpiryDate(),
                dto.getAmount(),
                manager
        );
    }

    public void attachSignedFile(String fileName, String filePath, Long fileSize) {
        if (fileName == null || filePath == null) {
            throw new IllegalArgumentException("File info is required");
        }

        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.uploadedAt = LocalDateTime.now();
        this.status = ContractStatus.SIGNED;
    }

    public boolean hasFile() {
        return this.filePath != null;
    }

    private void validate(String title, String counterpartyName) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Contract title is required");

        if (counterpartyName == null || counterpartyName.isBlank())
            throw new IllegalArgumentException("Counterparty name is required");
    }
}
