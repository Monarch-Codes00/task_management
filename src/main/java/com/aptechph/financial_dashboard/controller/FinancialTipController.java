package com.aptechph.financial_dashboard.controller;

import com.aptechph.financial_dashboard.dto.FinancialTipDto;
import com.aptechph.financial_dashboard.service.FinancialTipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financial-tips")
@RequiredArgsConstructor
@Tag(name = "Financial Tips", description = "Financial advice and tips APIs")
public class FinancialTipController {

    private final FinancialTipService financialTipService;

    @GetMapping
    @Operation(summary = "Get all financial tips")
    public ResponseEntity<List<FinancialTipDto>> getAllTips() {
        return ResponseEntity.ok(financialTipService.getAllTips());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get specific tip by ID")
    public ResponseEntity<FinancialTipDto> getTipById(@PathVariable Long id) {
        return ResponseEntity.ok(financialTipService.getTipById(id));
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        financialTipService.seedTipsIfEmpty();
    }
}
