package com.agro.crm.features.dashboard;

import com.agro.crm.features.dashboard.activityLog.ActivityLog;
import com.agro.crm.features.dashboard.activityLog.RecentActivitiesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponseDto getDashboard() {
        return dashboardService.getDashboardStats();
    }
    @GetMapping("/logs")
    public List<ActivityLog> getRecentLogs() {
        return  dashboardService.getRecentLogs();
    }

    @GetMapping("/charts")
    public ChartsResponseDto getChartsData() {
        return dashboardService.getChartsData();
    }
}