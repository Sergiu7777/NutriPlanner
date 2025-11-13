package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.entity.Client;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.entity.PlanRecipe;
import com.solutionscrafted.nutriplanner.entity.Recipe;
import com.solutionscrafted.nutriplanner.repository.ClientRepository;
import com.solutionscrafted.nutriplanner.repository.FoodRepository;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanGeneratorService {

    private final FoodRepository foodRepository;
    private final PlanRepository planRepository;
    private final RecipeRepository recipeRepository;
    private final ClientRepository clientRepository;

    public Plan generatePlan(Long idClient, String excludeTag) {
        //TODO- implement
        Client client = clientRepository.findById(idClient).orElseThrow(() -> new RuntimeException("Client not found!"));

        double target = client.getDailyCalories();
        List<Recipe> candidates = recipeRepository.findAll().stream()
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

        Plan plan = Plan.builder().client(client).dateCreated(LocalDateTime.now()).totalCalories(total).build();

        List<PlanRecipe> planRecipes = new ArrayList<>();
        for (Recipe r : recipes) {
            PlanRecipe p = new PlanRecipe();
            p.setPlan(plan);
            p.setRecipe(r);
            p.setMealTime("8:00"); //TODO: create enum MealTimeEnum (Breakfast - 8:00, Lunch - 12:00, Dinner - 18:00)
            planRecipes.add(p);
        }

        plan.setPlanRecipes(planRecipes);

        return planRepository.save(plan);
    }

    public byte[] exportPlanAsPdf(Long idPlan) {
        Plan plan = planRepository.findById(idPlan).orElseThrow(() -> new RuntimeException("Plan not found"));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);

            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("Meal Plan #" + plan.getId(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Spacer

            // Client Info
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Client: " + plan.getClient().getName(), normal));
            document.add(new Paragraph("Date Created: " + plan.getDateCreated(), normal));
            document.add(new Paragraph("Total Calories: " + plan.getTotalCalories(), normal));
            document.add(new Paragraph(" "));

            // Recipes List
            Font sectionTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            document.add(new Paragraph("Recipes Included:", sectionTitle));
            document.add(new Paragraph(" "));

            if (plan.getPlanRecipes() != null && !plan.getPlanRecipes().isEmpty()) {
                for (PlanRecipe pr : plan.getPlanRecipes()) {
                    document.add(new Paragraph("• " + pr.getRecipe().getName()
                            + " (" + pr.getMealTime() + ")", normal));
                }
            } else {
                document.add(new Paragraph("No recipes in this plan.", normal));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }
}

