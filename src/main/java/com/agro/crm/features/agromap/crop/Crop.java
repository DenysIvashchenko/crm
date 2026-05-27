package com.agro.crm.features.agromap.crop;

import com.agro.crm.features.agromap.feild.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "crops")
@Data

public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Пшеница, Кукуруза, Рапс

    private String season; // "2025" или "Весна 2025"

    @Column(name = "planting_date")
    private LocalDate plantingDate; // Когда посеяли

    @Column(name = "expected_yield")
    private BigDecimal expectedYield; // План урожайности (тонн/га) из твоей схемы

    private Double actualYield;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    @JsonIgnore
    private Field field;

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
