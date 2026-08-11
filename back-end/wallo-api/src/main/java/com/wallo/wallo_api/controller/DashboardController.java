package com.wallo.wallo_api.controller;

import com.wallo.wallo_api.dto.dashboard.CategorySummary;
import com.wallo.wallo_api.enums.TransactionType;
import com.wallo.wallo_api.security.UserDetailsImpl;
import com.wallo.wallo_api.service.DashboardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Endpoints de dados agregados para os dashboards.
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<CategorySummary>> byCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        List<CategorySummary> summary = dashboardService.summaryByCategory(
                userDetails.getUser(), type, start, end);
        return ResponseEntity.ok(summary);
    }
}