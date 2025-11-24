package com.solutionscrafted.nutriplanner.service;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.solutionscrafted.nutriplanner.dto.PdfResult;
import com.solutionscrafted.nutriplanner.entity.*;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlan;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanActivity;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanRecipe;
import com.solutionscrafted.nutriplanner.entity.dayplan.MealTimeEnum;
import com.solutionscrafted.nutriplanner.repository.ClientRepository;
import com.solutionscrafted.nutriplanner.repository.IngredientRepository;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfService {

    private final IngredientRepository ingredientRepository;
    private final PlanRepository planRepository;
    private final RecipeRepository recipeRepository;
    private final ClientRepository clientRepository;

    public PdfResult exportPlanAsPdf(Long planId) {
        log.info("Export plan to pdf for planId: {}", planId);

        Plan plan = planRepository.findById(planId).orElseThrow(() -> new RuntimeException("Plan not found"));

        String clientName = plan.getClient().getName();
        double calories = plan.getTotalCalories();

        String formattedDate = plan.getDateCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String filename = "Plan personalizat " + clientName + " " + formattedDate + ".pdf";
        log.info("FileName: {}", filename);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // LANDSCAPE A4
            Document document = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // ──────────────────────────────────────────────
            // TITLE
            // ──────────────────────────────────────────────
            Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD, Color.BLACK);
            Paragraph title = new Paragraph("PLAN ALIMENTAR PERSONALIZAT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph(clientName + " – " + calories + " kcal/zi", titleFont));
            document.add(new Paragraph("Data: " + LocalDate.now()));
            document.add(Chunk.NEWLINE);

            // ──────────────────────────────────────────────
            // INTRO TEXT
            // ──────────────────────────────────────────────
            Font body = new Font(Font.HELVETICA, 14);
            String intro = """
                    Felicitări pentru că ai ales să îți îmbunătățești stilul de viață prin nutriție!
                    Acest plan alimentar este creat special pentru tine, având în vedere obiectivele tale
                    personale, preferințele alimentare și stilul tău de viață. Urmează recomandările,
                    ascultă-ți corpul și fă pași mici, dar constanți, spre o versiune mai sănătoasă a ta.
                    """.replace("\n", " ").replace("//s+", " ");

            document.add(new Paragraph(intro, body));
            document.add(Chunk.NEWLINE);

            // ──────────────────────────────────────────────
            // MOTTO
            // ──────────────────────────────────────────────
            Paragraph motto = new Paragraph("\"Corpul tău este rezultatul deciziilor mici, repetate zilnic.\"", new Font(Font.BOLDITALIC, 12, Font.ITALIC, Color.GRAY));
            motto.setAlignment(Element.ALIGN_CENTER);
            document.add(motto);

            // ──────────────────────────────────────────────
            // DAYS TABLE (days horizontal, meals + activities vertical)
            // ──────────────────────────────────────────────
            document.newPage();

            // SORT DAYS
            List<DayPlan> days = plan.getDayPlans().stream().sorted(Comparator.comparing(DayPlan::getDay)).toList();

            // SPLIT INTO PAGES OF 7 DAYS
            List<List<DayPlan>> dayBlocks = splitPlanDaysByWeeks(days, 7);

            // GENERATE ONE PAGE PER 7 DAYS
            for (List<DayPlan> weekSplit : dayBlocks) {

                PdfPTable table = new PdfPTable(weekSplit.size() + 1); // 1 col for labels + N for days
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                // populate table for *these 7 days*
                addDaysHeaderRow(table, weekSplit);
                addMealRow(table, "Mic Dejun", weekSplit, MealTimeEnum.BREAKFAST);
                addMealRow(table, "Prânz", weekSplit, MealTimeEnum.LUNCH);
                addMealRow(table, "Cină", weekSplit, MealTimeEnum.DINNER);
                addActivitiesRow(table, weekSplit);
                addNotesRow(table, weekSplit);

                document.add(table);

                // Only start a new page if more day-blocks remain
                if (dayBlocks.indexOf(weekSplit) < dayBlocks.size() - 1) {
                    document.newPage();
                }
            }


            //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            // RECIPE SECTION — NO-SPLIT, TWO COLUMNS
            //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            document.newPage();

            // collect unique recipes used in this plan in order
            LinkedHashMap<Long, Recipe> uniqueRecipes = new LinkedHashMap<>();
            for (DayPlan dayPlan : days) {
                for (DayPlanRecipe dpr : dayPlan.getRecipes()) {
                    Recipe r = dpr.getRecipe();
                    if (r != null && r.getId() != null) {
                        uniqueRecipes.putIfAbsent(r.getId(), r);
                    }
                }
            }

            PdfContentByte canvas = writer.getDirectContent();

            // Column coordinates:
            float leftColLeft = document.left();
            float leftColRight = document.getPageSize().getWidth() / 2 - 20;

            float rightColLeft = document.getPageSize().getWidth() / 2 + 20;
            float rightColRight = document.right();

            float top = document.top();
            float bottom = document.bottom();

            // current column: 0 = left, 1 = right
            int currentColumn = 0;

            for (Recipe recipe : uniqueRecipes.values()) {

                // Build recipe block as a Phrase/Paragraph sequence
                java.util.List<Element> recipeElements = buildRecipeElements(recipe);

                // First simulate if fits in current column
                ColumnText ct = new ColumnText(canvas);
                ct.setSimpleColumn(currentColumn == 0 ? leftColLeft : rightColLeft, bottom, currentColumn == 0 ? leftColRight : rightColRight, top);

                for (Element el : recipeElements) ct.addElement(el);

                // simulate = true → check if it fits
                int status = ct.go(true);

                if (ColumnText.hasMoreText(status)) {
                    // doesn't fit → move to next column or page
                    if (currentColumn == 0) {
                        // switch to right column
                        currentColumn = 1;
                    } else {
                        // both columns used → new page
                        document.newPage();
                        currentColumn = 0;
                    }
                }

                // Draw recipe for real now
                ct = new ColumnText(canvas);
                ct.setSimpleColumn(currentColumn == 0 ? leftColLeft : rightColLeft, bottom, currentColumn == 0 ? leftColRight : rightColRight, top);

                for (Element el : recipeElements) ct.addElement(el);
                ct.go();

                // After placing recipe, move to next column
                if (currentColumn == 0) {
                    currentColumn = 1;
                } else {
                    // new page after finishing right column
                    document.newPage();
                    currentColumn = 0;
                }
            }


            document.close();
            return new PdfResult(out.toByteArray(), filename);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create PDF", e);
        }
    }

    private List<List<DayPlan>> splitPlanDaysByWeeks(List<DayPlan> days, int chunkSize) {
        List<List<DayPlan>> chunks = new ArrayList<>();
        for (int i = 0; i < days.size(); i += chunkSize) {
            chunks.add(days.subList(i, Math.min(days.size(), i + chunkSize)));
        }
        return chunks;
    }


    // ──────────────────────────────────────────────
    // HEADER ROW: empty corner + ZIUA 1, ZIUA 2, ...
    // ──────────────────────────────────────────────
    private void addDaysHeaderRow(PdfPTable table, List<DayPlan> days) {
        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
        Color headerBg = new Color(70, 130, 180);

        PdfPCell empty = new PdfPCell(new Phrase(""));
        empty.setBackgroundColor(headerBg);
        empty.setHorizontalAlignment(Element.ALIGN_CENTER);
        empty.setPadding(6);
        table.addCell(empty);

        for (DayPlan d : days) {
            PdfPCell cell = new PdfPCell(new Phrase("ZIUA " + d.getDay(), headerFont));
            cell.setBackgroundColor(headerBg);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6);
            table.addCell(cell);
        }
    }

    // ──────────────────────────────────────────────
    // MEAL ROW (Mic Dejun / Prânz / Cină)
    // ──────────────────────────────────────────────
    private void addMealRow(PdfPTable table, String mealLabel, List<DayPlan> days, MealTimeEnum mealTime) {

        // left label cell
        Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        PdfPCell mealCell = new PdfPCell(new Phrase(mealLabel, labelFont));
        mealCell.setBackgroundColor(new Color(230, 240, 250));
        mealCell.setPadding(6);
        table.addCell(mealCell);

        for (DayPlan dayPlan : days) {
            // all recipes for this meal for this day
            List<DayPlanRecipe> recipesForMeal = dayPlan.getRecipes().stream().filter(r -> r.getMealTime() == mealTime).toList();

            if (recipesForMeal.isEmpty()) {
                PdfPCell cell = new PdfPCell(new Phrase("-"));
                cell.setPadding(5);
                table.addCell(cell);
                continue;
            }

            StringBuilder sb = new StringBuilder();
            double totalMealCalories = 0.0;

            for (DayPlanRecipe dpr : recipesForMeal) {
                Recipe r = dpr.getRecipe();
                if (r == null) continue;

                double servings = dpr.getServings() != null ? dpr.getServings() : 1.0;
                double recipeCalories = (r.getTotalCalories() != null ? r.getTotalCalories() : 0.0) * servings;
                totalMealCalories += recipeCalories;

                sb.append(r.getName()).append(" (").append((int) recipeCalories).append(" kcal)").append("\n");
            }

            sb.append("Total: ").append((int) totalMealCalories).append(" kcal");

            PdfPCell cell = new PdfPCell(new Phrase(sb.toString()));
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    // ──────────────────────────────────────────────
    // ACTIVITIES ROW
    // ──────────────────────────────────────────────
    private void addActivitiesRow(PdfPTable table, List<DayPlan> days) {

        Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        PdfPCell labelCell = new PdfPCell(new Phrase("Activități", labelFont));
        labelCell.setBackgroundColor(new Color(230, 240, 250));
        labelCell.setPadding(6);
        table.addCell(labelCell);

        for (DayPlan dayPlan : days) {
            List<DayPlanActivity> activities = dayPlan.getActivities();

            if (activities == null || activities.isEmpty()) {
                PdfPCell cell = new PdfPCell(new Phrase("-"));
                cell.setPadding(5);
                table.addCell(cell);
                continue;
            }

            StringBuilder sb = new StringBuilder();
            for (DayPlanActivity dpa : activities) {
                SportActivity act = dpa.getActivity();
                if (act == null) continue;

                sb.append(act.getName());
                if (act.getDuration() != null) {
                    sb.append(" (").append(act.getDuration()).append(" min)");
                }
                sb.append("\n");
            }

            PdfPCell cell = new PdfPCell(new Phrase(sb.toString().trim()));
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    // ──────────────────────────────────────────────
    // NOTES ROW
    // ──────────────────────────────────────────────
    private void addNotesRow(PdfPTable table, List<DayPlan> days) {

        Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        PdfPCell labelCell = new PdfPCell(new Phrase("Observații", labelFont));
        labelCell.setBackgroundColor(new Color(230, 240, 250));
        labelCell.setPadding(6);
        table.addCell(labelCell);

        for (DayPlan dayPlan : days) {
            String dayPlanNote = dayPlan.getNote();

            if (dayPlanNote == null || dayPlanNote.isEmpty()) {
                PdfPCell cell = new PdfPCell(new Phrase("-"));
                cell.setPadding(5);
                table.addCell(cell);
                continue;
            }

            PdfPCell cell = new PdfPCell(new Phrase(dayPlanNote.trim()));
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    private String formatIngredient(IngredientRecipe ir) {
        Ingredient ing = ir.getIngredient();
        if (ing == null) return "-";

        String name = ing.getName() != null ? ing.getName() : "Ingredient";
        Double qty = ir.getQuantity();

        if (qty != null) {
            return "- " + name + " – " + qty.intValue() + " g";
        } else {
            return "- " + name;
        }
    }

    private List<Element> buildRecipeElements(Recipe recipe) {
        List<Element> list = new ArrayList<>();

        // Title
        Paragraph title = new Paragraph(recipe.getName(), new Font(Font.HELVETICA, 16, Font.BOLD));
        title.setSpacingBefore(10);
        title.setSpacingAfter(6);
        list.add(title);

        // Calories
        if (recipe.getTotalCalories() != null) {
            Paragraph kcal = new Paragraph("Total calorii: " + recipe.getTotalCalories().intValue() + " kcal", new Font(Font.HELVETICA, 11, Font.BOLD));
            kcal.setSpacingAfter(6);
            list.add(kcal);
        }

        // Ingredients
        Paragraph ingHeader = new Paragraph("Ingrediente:", new Font(Font.HELVETICA, 13, Font.BOLD));
        ingHeader.setSpacingAfter(4);
        list.add(ingHeader);

        if (recipe.getIngredients() != null) {
            for (IngredientRecipe ir : recipe.getIngredients()) {
                list.add(new Paragraph(formatIngredient(ir), new Font(Font.HELVETICA, 11)));
            }
        }

        list.add(new Paragraph(" "));

        // Instructions
        Paragraph instrHeader = new Paragraph("Instrucțiuni:", new Font(Font.HELVETICA, 13, Font.BOLD));
        instrHeader.setSpacingAfter(4);
        list.add(instrHeader);

        Paragraph instrText = new Paragraph(recipe.getInstructions() != null ? recipe.getInstructions() : "-", new Font(Font.HELVETICA, 11));
        instrText.setSpacingAfter(15);
        list.add(instrText);

        return list;
    }

    // ──────────────────────────────────────────────
    // RECIPE DETAILS PAGE (one per unique recipe)
    // ──────────────────────────────────────────────
    private void addRecipeDetailsPage(Document document, Recipe recipe) throws DocumentException {
        // Title
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLACK);
        Paragraph title = new Paragraph(recipe.getName(), titleFont);
        title.setAlignment(Element.ALIGN_LEFT);
        document.add(title);

        document.add(Chunk.NEWLINE);

        // Calories
        if (recipe.getTotalCalories() != null) {
            Font smallBold = new Font(Font.HELVETICA, 12, Font.BOLD);
            document.add(new Paragraph("Total calorii: " + recipe.getTotalCalories() + " kcal", smallBold));
            document.add(Chunk.NEWLINE);
        }

        // Ingredients
        Font sectionTitleFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        document.add(new Paragraph("Ingrediente:", sectionTitleFont));
        document.add(Chunk.NEWLINE);

        if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
            for (IngredientRecipe ir : recipe.getIngredients()) {
                Ingredient ing = ir.getIngredient();
                if (ing == null) continue;

                String name = ing.getName() != null ? ing.getName() : "Ingredient";
                Double qty = ir.getQuantity();

                StringBuilder line = new StringBuilder("- ");
                line.append(name);
                if (qty != null) {
                    line.append(" – ").append(qty.intValue()).append(" g");
                }

                document.add(new Paragraph(line.toString(), new Font(Font.HELVETICA, 11)));
            }
        } else {
            document.add(new Paragraph("–", new Font(Font.HELVETICA, 11)));
        }

        document.add(Chunk.NEWLINE);

        // Instructions
        document.add(new Paragraph("Instrucțiuni:", sectionTitleFont));
        document.add(Chunk.NEWLINE);

        if (recipe.getInstructions() != null && !recipe.getInstructions().isBlank()) {
            document.add(new Paragraph(recipe.getInstructions(), new Font(Font.HELVETICA, 11)));
        } else {
            document.add(new Paragraph("Nu există instrucțiuni disponibile.", new Font(Font.HELVETICA, 11)));
        }
    }

}

