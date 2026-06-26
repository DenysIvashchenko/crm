package com.agro.crm.features.dashboard;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @Column(nullable = false, length = 1000)
    private String details;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    private ActivityLog(
            AuditAction action,
            String details,
            String email
    ) {
        this.action = action;
        this.details = details;
        this.userEmail = email;
    }

    public static ActivityLog create(
            AuditAction action,
            String details,
            String email
    ){
        return new ActivityLog(action,details, email);
    }
}
