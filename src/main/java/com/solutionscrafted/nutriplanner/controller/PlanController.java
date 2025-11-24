package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.dto.PdfResult;
import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.dto.PlanRequestDto;
import com.solutionscrafted.nutriplanner.service.PdfService;
import com.solutionscrafted.nutriplanner.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<PlanDto> generatePlan(@RequestBody PlanRequestDto requestDto) {
        return ResponseEntity.ok(planService.generatePlan(requestDto));
    }

    @PostMapping("/{plan_id}")
    public ResponseEntity<PlanDto> updatePlan(@PathVariable("plan_id") Long planId, @RequestBody PlanRequestDto requestDto) {
        return ResponseEntity.ok(planService.updatePlan(planId, requestDto));
    }

    @GetMapping("/{client_id}")
    public ResponseEntity<List<PlanDto>> getPlansByClientId(@PathVariable("client_id") Long clientId) {
        return ResponseEntity.ok(planService.getPlansByClientId(clientId));
    }

    @GetMapping("/{plan_id}/pdf")
    public ResponseEntity<byte[]> exportPlanAsPdf(@PathVariable("plan_id") Long planId) {
        PdfResult pdf = pdfService.exportPlanAsPdf(planId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdf.filename())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.pdf());
    }
}
