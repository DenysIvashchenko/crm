package com.agro.crm.features.farmers;

import com.agro.crm.features.agromap.feild.Field;
import com.agro.crm.features.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farmers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    private String phone;
    private String region;
    private String color;

    @Email
    private String email;

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
    @Fetch(FetchMode.SUBSELECT)
    private List<Field> fields = new ArrayList<>();

    public static Farmer create(
            FarmerCreateRequest dto,
            User manager,
            String color
    ) {

        Farmer farmer = new Farmer();

        farmer.fullName = dto.getFullName();
        farmer.phone = dto.getPhone();
        farmer.email = dto.getEmail();
        farmer.region = dto.getRegion();
        farmer.totalLandHa = dto.getTotalLandHa();
        farmer.status = FarmerStatus.NEW;
        farmer.color = color;
        farmer.manager = manager;

        return farmer;
    }

    public void updateProfile(
            String fullName,
            String phone,
            String region,
            Double totalLandHa,
            FarmerStatus status
    ) {
        this.fullName = fullName;
        this.phone = phone;
        this.region = region;
        this.totalLandHa = totalLandHa;
        this.status = status;
    }

    public void addField(Field field) {
        field.assignTo(this);
        fields.add(field);
    }
}