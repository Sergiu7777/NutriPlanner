package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.controller.requestbody.IngredientRequestDto;
import com.solutionscrafted.nutriplanner.dto.IngredientDto;
import com.solutionscrafted.nutriplanner.entity.Ingredient;
import com.solutionscrafted.nutriplanner.mappers.IngredientMapper;
import com.solutionscrafted.nutriplanner.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper mapper;

    public Page<IngredientDto> getIngredients(Pageable pageable) {
        var ingredientPage = ingredientRepository.findAll(pageable);

        var dtoList = mapper.toIngredientDtoList(ingredientPage.getContent());

        return new PageImpl<>(dtoList, pageable, ingredientPage.getTotalElements());
    }

    public IngredientDto getIngredientById(Long id) {
        var ingredient = ingredientRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find ingredient with id: " + id));

        return mapper.toIngredientDto(ingredient);
    }

    public IngredientDto createIngredient(IngredientRequestDto requestDto) {
        var ingredient = ingredientRepository.saveAndFlush(mapper.toIngredient(requestDto));

        return mapper.toIngredientDto(ingredient);
    }

    public IngredientDto updateIngredient(Long id, IngredientRequestDto requestDto) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingredient not found for id: " + id));

        ingredient.setName(requestDto.name());
        ingredient.setCalories(requestDto.calories());
        ingredient.setProtein(requestDto.protein());
        ingredient.setCarbs(requestDto.carbs());
        ingredient.setFat(requestDto.fat());
        ingredient.setCategory(requestDto.category());
        ingredient.setTags(requestDto.tags());

        Ingredient updated = ingredientRepository.save(ingredient);
        return mapper.toIngredientDto(updated);
    }

    public void deleteIngredient(Long id) {
        log.info("Deleting ingredient for ingredientId: {}.", id);

        ingredientRepository.findById(id).ifPresentOrElse(ingredientRepository::delete, () -> {
            log.warn("Ingredient not found with id: {}", id);
        });
    }
}
