package com.aptechph.financial_dashboard.controller;

import com.aptechph.financial_dashboard.dto.ReportDto;
import com.aptechph.financial_dashboard.dto.ReportRequest;
import com.aptechph.financial_dashboard.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Financial reports management APIs")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @Operation(summary = "Get all reports for current user")
    public ResponseEntity<List<ReportDto>> getAllReports() {
        List<ReportDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get report by ID")
    public ResponseEntity<ReportDto> getReportById(@PathVariable Long id) {
        ReportDto report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/month/{yearMonth}")
    @Operation(summary = "Get report by month")
    public ResponseEntity<ReportDto> getReportByMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        Optional<ReportDto> report = reportService.getReportByMonth(yearMonth);
        return report.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new monthly report")
    public ResponseEntity<ReportDto> createReport(@Valid @RequestBody ReportRequest request) {
        ReportDto report = reportService.createReport(request);
        return ResponseEntity.ok(report);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing report")
    public ResponseEntity<ReportDto> updateReport(@PathVariable Long id,
                                                @Valid @RequestBody ReportRequest request) {
        ReportDto report = reportService.updateReport(id, request);
        return ResponseEntity.ok(report);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a report")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/range")
    @Operation(summary = "Get reports within month range")
    public ResponseEntity<List<ReportDto>> getReportsByMonthRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth startMonth,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth endMonth) {
        List<ReportDto> reports = reportService.getReportsByMonthRange(startMonth, endMonth);
        return ResponseEntity.ok(reports);
    }
}