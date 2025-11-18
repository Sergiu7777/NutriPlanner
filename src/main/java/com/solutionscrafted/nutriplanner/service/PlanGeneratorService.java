package com.solutionscrafted.nutriplanner.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.solutionscrafted.nutriplanner.entity.*;
import com.solutionscrafted.nutriplanner.repository.ClientRepository;
import com.solutionscrafted.nutriplanner.repository.FoodRepository;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanGeneratorService {

  private final FoodRepository foodRepository;
  private final PlanRepository planRepository;
  private final RecipeRepository recipeRepository;
  private final ClientRepository clientRepository;

  public Plan generatePlan(Long idClient, String excludeTag) {
    // TODO- implement
    Client client =
        clientRepository
            .findById(idClient)
            .orElseThrow(() -> new RuntimeException("Client not found!"));

    double target = client.getDailyCalories();
    List<Recipe> candidates =
        recipeRepository.findAll().stream()
            .filter(r -> !r.getTags().contains(excludeTag))
            .collect(Collectors.toList());

    Collections.shuffle(candidates);

    List<Recipe> recipes = new ArrayList<>();
    double total = 0;

    for (Recipe r : candidates) {
      if (total + r.getTotalCalories() <= target + 100) {
        recipes.add(r);
        total += r.getTotalCalories();
      }
      if (recipes.size() == 3) break;
    }

    Plan plan =
        Plan.builder().client(client).dateCreated(LocalDateTime.now()).totalCalories(total).build();

    List<PlanRecipe> planRecipes = new ArrayList<>();
    for (Recipe r : recipes) {
      PlanRecipe p = new PlanRecipe();
      p.setPlan(plan);
      p.setRecipe(r);
      p.setMealTime(
          String.valueOf(r.getMealTime())); // TODO: add this in recipes and convert from string

      planRecipes.add(p);
    }

    plan.setPlanRecipes(planRecipes);

    return planRepository.save(plan);
  }

  public byte[] exportPlanAsPdf(Long idPlan) {
    Plan plan =
        planRepository.findById(idPlan).orElseThrow(() -> new RuntimeException("Plan not found"));
    var clientName = plan.getClient().getName();
    var calories = plan.getTotalCalories();

    log.info(plan.getPlanRecipes().toString());

    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      Document document = new Document(PageSize.A4, 36, 36, 36, 36);
      PdfWriter.getInstance(document, out);
      document.open();
      // ──────────────────────────────────────────────
      // TITLE
      // ──────────────────────────────────────────────
      Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD, Color.BLACK);
      Paragraph title = new Paragraph("PLAN ALIMENTAR PERSONALIZAT", titleFont);
      title.setAlignment(Element.ALIGN_CENTER);
      document.add(title);
      document.add(new Paragraph(" "));
      Font subFont = new Font(Font.HELVETICA, 16, Font.BOLD);
      document.add(new Paragraph(clientName + " – " + calories + " kcal/zi", subFont));
      document.add(new Paragraph("Data: " + LocalDate.now()));
      document.add(Chunk.NEWLINE);
      // ──────────────────────────────────────────────
      // INTRO TEXT
      // ──────────────────────────────────────────────
      Font body = new Font(Font.HELVETICA, 11);
      String intro =
          """
                    Felicitări pentru că ai ales să îți îmbunătățești stilul de viață prin nutriție!
                    Acest plan alimentar este creat special pentru tine, având în vedere obiectivele tale
                    personale, preferințele alimentare și stilul tău de viață. Urmează recomandările,
                    ascultă-ți corpul și fă pași mici, dar constanți, spre o versiune mai sănătoasă a ta.
                    """;
      document.add(new Paragraph(intro, body));
      document.add(Chunk.NEWLINE);
      // ──────────────────────────────────────────────
      // DAYS TABLE (EXAMPLE: DAY 1 → DAY 7)
      // ────────────────────────────────────────────── //TODO: create for loop and populate from
      // the planRecipes
      PdfPTable table = new PdfPTable(4);
      table.setWidthPercentage(100);
      table.setSpacingBefore(10);
      Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
      Color headerBg = new Color(70, 130, 180);
      addHeader(table, "Ziua");
      addHeader(table, "Mic Dejun");
      addHeader(table, "Prânz");
      addHeader(table, "Cină");
      // Example Day 1 (from uploaded document)
      table.addCell("ZIUA 1");
      table.addCell("Pancakes proteice\nSana 150 ml");
      table.addCell("Mâncare de ciuperci cu mazăre\nMușchi de porc 120g");
      table.addCell("Ruladă de dovlecei 200g");
      // Day 2
      table.addCell("ZIUA 2");
      table.addCell("Omletă cu spanac\nPâine integrală 80g");
      table.addCell("Chifteluțe de vită + legume la cuptor");
      table.addCell("Wrap cu șuncă și avocado");
      // Add days 3–7 here similar...
      document.add(table);
      document.add(Chunk.NEWLINE);
      // ──────────────────────────────────────────────
      // FOOTER TEXT
      // ──────────────────────────────────────────────
      Paragraph footer =
          new Paragraph(
              "\"Corpul tău este rezultatul deciziilor mici, repetate zilnic.\"",
              new Font(Font.HELVETICA, 12, Font.ITALIC, Color.GRAY));
      footer.setAlignment(Element.ALIGN_CENTER);
      document.add(footer);
      document.close();
      return out.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create PDF", e);
    }
  }

  private void addHeader(PdfPTable table, String text) {
    Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
    PdfPCell cell = new PdfPCell(new Phrase(text, headerFont));
    cell.setBackgroundColor(new Color(70, 130, 180));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setPadding(6);
    table.addCell(cell);
  }
}
