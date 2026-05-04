package com.agro.crm.features.farmers;

import com.agro.crm.features.agromap.feild.Field;
import com.agro.crm.features.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farmers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    private String phone;
    private String region;

    @Enumerated(EnumType.STRING)
    private FarmerStatus status;

    @Column(name = "total_land_ha")
    private Double totalLandHa;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @JsonIgnoreProperties({"password", "roles", "createdAt", "updatedAt"})
    private User manager;

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("farmer")
    private List<Field> fields = new ArrayList<>();
}