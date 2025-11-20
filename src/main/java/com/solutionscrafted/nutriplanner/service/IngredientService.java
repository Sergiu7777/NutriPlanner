package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.dto.IngredientDto;
import com.solutionscrafted.nutriplanner.mappers.NutriMapper;
import com.solutionscrafted.nutriplanner.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final NutriMapper nutriMapper;

    public List<IngredientDto> getIngredients() {
        return nutriMapper.toIngredientDtoList(ingredientRepository.findAll());
    }

    public IngredientDto getIngredientById(Long id) {
        var ingredient =
                ingredientRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("Could not find ingredient with id: " + id));

        return nutriMapper.toIngredientDto(ingredient);
    }

    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        var ingredient = ingredientRepository.saveAndFlush(nutriMapper.toIngredient(ingredientDto));

        return nutriMapper.toIngredientDto(ingredient);
    }
}
