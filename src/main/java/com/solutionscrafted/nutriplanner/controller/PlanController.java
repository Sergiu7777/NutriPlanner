package com.solutionscrafted.nutriplanner.controller;


import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import com.solutionscrafted.nutriplanner.service.PlanGeneratorService;
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

    private final PlanRepository planRepository;
    private final PlanGeneratorService planService;

    @PostMapping("/generate")
    public ResponseEntity<Plan> generatePlan(@RequestParam Long id_client,
                                             @RequestParam(required = false) String exclude_tag) {
        return ResponseEntity.ok(planService.generatePlan(id_client, exclude_tag));
    }

    @GetMapping("/{id_client}")
    public ResponseEntity<List<Plan>> getPlansByClient(@PathVariable("id_client") Long idClient) {
        //TODO: first check if client exists
        return ResponseEntity.ok(planRepository.findByClient_Id(idClient));
    }

    @GetMapping("/{id_plan}/pdf")
    public ResponseEntity<byte[]> exportPlanAsPdf(@PathVariable("id_plan") Long idPlan) {
        byte[] pdf = planService.exportPlanAsPdf(idPlan);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plan-" + idPlan + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
