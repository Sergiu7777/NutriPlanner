package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.dto.RecipeDto;
import com.solutionscrafted.nutriplanner.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getRecipes() {
        log.info("Get recipes request: /recipes.");

        return ResponseEntity.ok(recipeService.getRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable long id) {
        log.info("Get recipe by id request: /recipes/{}.", id);

        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto request) {
        log.info("Create recipe request: /recipes. Body: {}.", request);

        var recipe = recipeService.createRecipe(request);
        return ResponseEntity.created(URI.create(String.format("/recipes/%s", recipe.id())))
                .body(recipe);
    }
}
