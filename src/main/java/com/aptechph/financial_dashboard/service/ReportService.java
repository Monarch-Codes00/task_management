package com.aptechph.financial_dashboard.service;

import com.aptechph.financial_dashboard.dto.ReportDto;
import com.aptechph.financial_dashboard.dto.ReportRequest;
import com.aptechph.financial_dashboard.entity.Report;
import com.aptechph.financial_dashboard.entity.User;
import com.aptechph.financial_dashboard.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<ReportDto> getAllReports() {
        User currentUser = authService.getCurrentUser();
        return reportRepository.findByUserOrderByMonthDesc(currentUser)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReportDto getReportById(Long id) {
        User currentUser = authService.getCurrentUser();
        Report report = reportRepository.findById(id)
                .filter(r -> r.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Report not found"));
        return convertToDto(report);
    }

    @Transactional(readOnly = true)
    public Optional<ReportDto> getReportByMonth(YearMonth month) {
        User currentUser = authService.getCurrentUser();
        return reportRepository.findByUserAndMonth(currentUser, month)
                .map(this::convertToDto);
    }

    @Transactional
    public ReportDto createReport(ReportRequest request) {
        User currentUser = authService.getCurrentUser();

        // Check if report already exists for this month
        Optional<Report> existing = reportRepository.findByUserAndMonth(currentUser, request.getMonth());
        if (existing.isPresent()) {
            throw new RuntimeException("Report already exists for this month");
        }

        Report report = new Report();
        report.setMonth(request.getMonth());
        report.setIncome(request.getIncome());
        report.setExpenses(request.getExpenses());
        report.setUser(currentUser);

        Report saved = reportRepository.save(report);
        return convertToDto(saved);
    }

    @Transactional
    public ReportDto updateReport(Long id, ReportRequest request) {
        User currentUser = authService.getCurrentUser();
        Report report = reportRepository.findById(id)
                .filter(r -> r.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        report.setMonth(request.getMonth());
        report.setIncome(request.getIncome());
        report.setExpenses(request.getExpenses());

        Report saved = reportRepository.save(report);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteReport(Long id) {
        User currentUser = authService.getCurrentUser();
        Report report = reportRepository.findById(id)
                .filter(r -> r.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Report not found"));
        reportRepository.delete(report);
    }

    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByMonthRange(YearMonth startMonth, YearMonth endMonth) {
        User currentUser = authService.getCurrentUser();
        return reportRepository.findByUserAndMonthRange(currentUser, startMonth, endMonth)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ReportDto convertToDto(Report report) {
        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setMonth(report.getMonth());
        dto.setIncome(report.getIncome());
        dto.setExpenses(report.getExpenses());
        dto.setNetIncome(report.getNetIncome());
        return dto;
    }
}