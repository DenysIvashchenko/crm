package com.agro.crm.features.equipment;

import com.agro.crm.features.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipment_logs")
@Data
public class EquipmentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    @JsonIgnore
    private Equipment equipment;   // какая техника

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;             // кто работал

    private Double mileageAdded;   // сколько часов/км за смену
    private Double fuelUsed;       // литров топлива
    private String note;           // комментарий

    @CreationTimestamp
    private LocalDateTime createdAt;
}
