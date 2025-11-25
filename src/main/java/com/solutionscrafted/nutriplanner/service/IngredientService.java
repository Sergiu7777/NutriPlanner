package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.dto.IngredientDto;
import com.solutionscrafted.nutriplanner.dto.IngredientRequestDto;
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
}
