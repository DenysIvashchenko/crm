package com.agro.crm.features.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final ActivityLogRepository activityLogRepository;

    @Transactional
    public void logAction(AuditAction action, String details) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        ActivityLog log =  ActivityLog.create(action,details,email);

        activityLogRepository.save(log);
    }
}
