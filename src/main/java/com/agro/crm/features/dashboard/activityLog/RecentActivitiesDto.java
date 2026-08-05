package com.agro.crm.features.dashboard.activityLog;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RecentActivitiesDto {
    private List<ActivityLog> recentActivities;
}
