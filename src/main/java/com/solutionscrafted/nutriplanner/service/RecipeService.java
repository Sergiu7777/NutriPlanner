package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.dto.RecipeDto;
import com.solutionscrafted.nutriplanner.mappers.NutriMapper;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

  private final RecipeRepository recipeRepository;
  private final NutriMapper nutriMapper;

  public List<RecipeDto> getRecipes() {
    return nutriMapper.toRecipeDtoList(recipeRepository.findAll());
  }

  public RecipeDto createRecipe(@Valid RecipeDto recipeDto) {
    var recipe = recipeRepository.saveAndFlush(nutriMapper.toRecipe(recipeDto));

    return nutriMapper.toDto(recipe);
  }

  public RecipeDto getRecipeById(long id) {
    var recipe =
        recipeRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

    return nutriMapper.toDto(recipe);
  }
}
