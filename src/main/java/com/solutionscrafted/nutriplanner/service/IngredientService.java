package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.dto.IngredientDto;
import com.solutionscrafted.nutriplanner.mappers.IngredientMapper;
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
    private final IngredientMapper mapper;

    public List<IngredientDto> getIngredients() {
        return mapper.toIngredientDtoList(ingredientRepository.findAll());
    }

    public IngredientDto getIngredientById(Long id) {
        var ingredient = ingredientRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find ingredient with id: " + id));

        return mapper.toIngredientDto(ingredient);
    }

    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        var ingredient = ingredientRepository.saveAndFlush(mapper.toIngredient(ingredientDto));

        return mapper.toIngredientDto(ingredient);
    }
}
