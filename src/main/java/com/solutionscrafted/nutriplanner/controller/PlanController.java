package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.service.PlanGeneratorService;
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
    private final PlanGeneratorService planGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<Plan> generatePlan(
            @RequestParam("client_id") Long clientId, @RequestParam(value = "exclude_tag", required = false) String excludeTag) {
        return ResponseEntity.ok(planGeneratorService.generatePlan(clientId, excludeTag));
    }

    @GetMapping("/{client_id}")
    public ResponseEntity<List<PlanDto>> getPlansByClientId(@PathVariable("client_id") Long clientId) {
        return ResponseEntity.ok(planService.getPlansByClientId(clientId));
    }

    @GetMapping("/{plan_id}/pdf")
    public ResponseEntity<byte[]> exportPlanAsPdf(@PathVariable("plan_id") Long planId) {
        byte[] pdf = planGeneratorService.exportPlanAsPdf(planId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plan-" + planId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
