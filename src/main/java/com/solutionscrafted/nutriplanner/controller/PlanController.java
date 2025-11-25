package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.controller.requestbody.PlanRequestDto;
import com.solutionscrafted.nutriplanner.dto.PdfResult;
import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.service.PdfService;
import com.solutionscrafted.nutriplanner.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final PdfService pdfService;

    @GetMapping
    public ResponseEntity<List<PlanDto>> getPlans() {
        log.info("Get plans request: /plans.");

        return ResponseEntity.ok(planService.getAllPlans());
    }

    @PostMapping("/generate")
    public ResponseEntity<PlanDto> generatePlan(@RequestBody PlanRequestDto requestDto) {
        log.info("Create plan for client request: /plans/generate. Body: {}.", requestDto);

        return ResponseEntity.ok(planService.generatePlan(requestDto));
    }

    @PostMapping("/{plan_id}")
    public ResponseEntity<PlanDto> updatePlan(@PathVariable("plan_id") Long planId, @RequestBody PlanRequestDto requestDto) {
        log.info("Update plan for client request: /plans/{}. Body: {}.", planId, requestDto);

        return ResponseEntity.ok(planService.updatePlan(planId, requestDto));
    }

    @DeleteMapping("/{plan_id}")
    public ResponseEntity<Void> deletePlan(@PathVariable("plan_id") Long planId) {
        log.info("Delete plan request: /plans/{}.", planId);

        planService.deletePlan(planId);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{client_id}")
    public ResponseEntity<List<PlanDto>> getPlansByClientId(@PathVariable("client_id") Long clientId) {
        log.info("Get plans for client request: /plans/{}.", clientId);

        return ResponseEntity.ok(planService.getPlansByClientId(clientId));
    }

    @GetMapping("/{plan_id}/pdf")
    public ResponseEntity<byte[]> exportPlanAsPdf(@PathVariable("plan_id") Long planId) {
        log.info("Export plan to pdf request: /plans/{}.", planId);

        PdfResult pdf = pdfService.exportPlanAsPdf(planId);
        String encodedFilename = URLEncoder.encode(pdf.filename(), StandardCharsets.UTF_8).replace("+", "%20");

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pdf.filename() + "\"; filename*=UTF-8''" + encodedFilename).contentType(MediaType.APPLICATION_PDF).body(pdf.pdf());
    }
}
