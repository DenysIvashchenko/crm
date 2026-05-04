package com.agro.crm.features.agromap.feild;

import com.agro.crm.features.agromap.crop.Crop;
import com.agro.crm.features.farmers.Farmer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "fields")
@Data

public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "area_ha")
    private BigDecimal areaHa;

    @Column(name = "soil_type", length = 50)
    @Enumerated(EnumType.STRING)
    private SoilType soilType;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id")
    @JsonIgnore
    private Farmer farmer;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Crop> crops;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
