package com.agro.crm.features.equipment;

import com.agro.crm.features.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
@Data
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "John Deere 8R", "Drone Sprayer v2"

    private String type; // TRACTOR, HARVESTER, DRONE

    @Column(name = "license_plate")
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status = EquipmentStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    @JsonIgnoreProperties({"password", "roles", "createdAt"})
    private User operator; // Link to the employee (Operator)

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
