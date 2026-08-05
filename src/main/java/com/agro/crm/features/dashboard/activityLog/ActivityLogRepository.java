package com.agro.crm.features.dashboard.activityLog;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>{
    List<ActivityLog> findByOrderByTimestampDesc(Pageable pageable);

    Page<ActivityLog> findAll(Pageable pageable);
}