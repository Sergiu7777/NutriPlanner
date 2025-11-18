package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.service.PlanGeneratorService;
import com.solutionscrafted.nutriplanner.service.PlanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

  private final PlanService planService;
  private final PlanGeneratorService planGeneratorService;

  @PostMapping("/generate")
  public ResponseEntity<Plan> generatePlan(
      @RequestParam("id_client") Long idClient, @RequestParam(value = "exclude_tag", required = false) String excludeTag) {
    return ResponseEntity.ok(planGeneratorService.generatePlan(idClient, excludeTag));
  }

  @GetMapping("/{id_client}")
  public ResponseEntity<List<PlanDto>> getPlansByClient(@PathVariable("id_client") Long idClient) {
    return ResponseEntity.ok(planService.getPlansByClientId(idClient));
  }

  @GetMapping("/{id_plan}/pdf")
  public ResponseEntity<byte[]> exportPlanAsPdf(@PathVariable("id_plan") Long idPlan) {
    byte[] pdf = planGeneratorService.exportPlanAsPdf(idPlan);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plan-" + idPlan + ".pdf")
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }
}
