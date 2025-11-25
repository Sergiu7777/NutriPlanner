package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.controller.requestbody.RecipeRequestDto;
import com.solutionscrafted.nutriplanner.dto.RecipeDto;
import com.solutionscrafted.nutriplanner.mappers.RecipeMapper;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper mapper;

    public List<RecipeDto> getRecipes() {
        return mapper.toRecipeDtoList(recipeRepository.findAll());
    }

    public RecipeDto createRecipe(@Valid RecipeRequestDto recipeDto) {
        var recipe = recipeRepository.saveAndFlush(mapper.toRecipe(recipeDto));

        return mapper.toRecipeDto(recipe);
    }

    public RecipeDto updateRecipe(Long id, @Valid RecipeRequestDto requestDto) {
        var recipe = recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Plan not found for id: " + id));

        recipe.setName(requestDto.name());
        recipe.setInstructions(requestDto.instructions());
        recipe.setTotalCalories(requestDto.totalCalories());
        recipe.setTags(requestDto.tags());

        return mapper.toRecipeDto(recipeRepository.save(recipe));
    }

    public RecipeDto getRecipeById(long id) {
        var recipe =
                recipeRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        return mapper.toRecipeDto(recipe);
    }
}
