package com.agro.crm.features.equipment;

import com.agro.crm.features.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "equipment")
@Getter
@ToString(exclude = "operator")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType type;

    @Column(name = "license_plate")
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status = EquipmentStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    private User operator;

    @Column(name = "year_made")
    private Integer yearMade;

    private Double mileage;

    @Column(name = "next_service")
    private LocalDate nextService;

    @Column(name = "device_id")
    private String deviceId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public static Equipment create(
            String name,
            EquipmentType type,
            String licensePlate,
            Integer yearMade,
            Double mileage,
            LocalDate nextService,
            String deviceId
    ) {
        Equipment equipment = new Equipment();
        equipment.name = name;
        equipment.type = type;
        equipment.licensePlate = licensePlate;
        equipment.yearMade = yearMade;
        equipment.mileage = mileage;
        equipment.nextService = nextService;
        equipment.deviceId = deviceId;

        return equipment;
    }

    public void assignOperator(User operator) {
        if (this.status == EquipmentStatus.MAINTENANCE) {
            throw new IllegalStateException("can not assign operator, equipment on maintenance");
        }
        this.operator = operator;
        this.status = EquipmentStatus.IN_USE;
    }

    public void releaseOperator() {
        this.operator = null;
        this.status = EquipmentStatus.AVAILABLE;
    }

    public void updateMileage(Double currentMileage) {
        if (currentMileage < this.mileage) {
            throw new IllegalArgumentException("can not be less than privies");
        }
        this.mileage = currentMileage;
    }

    public void updateStatus(EquipmentStatus status) {
        this.status = status;
    }

    public void sendToMaintenance(LocalDate nextServiceDate) {
        this.status = EquipmentStatus.MAINTENANCE;
        this.operator = null;
        this.nextService = nextServiceDate;
    }

    public void updateDetails(String name, String licensePlate, String deviceId) {
        this.name = name;
        this.licensePlate = licensePlate;
        this.deviceId = deviceId;
    }
}
