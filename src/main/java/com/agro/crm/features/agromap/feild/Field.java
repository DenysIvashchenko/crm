package com.agro.crm.features.agromap.feild;

import com.agro.crm.features.agromap.crop.Crop;
import com.agro.crm.features.farmers.Farmer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "boundary_coordinates", columnDefinition = "jsonb")
    private String boundaryCoordinates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id")
    @JsonIgnore
    private Farmer farmer;

    @Column
    private String colorField;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Crop> crops;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
