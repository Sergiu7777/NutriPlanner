package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.dto.FoodDto;
import com.solutionscrafted.nutriplanner.mappers.NutriMapper;
import com.solutionscrafted.nutriplanner.repository.FoodRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;
  private final NutriMapper nutriMapper;

  public List<FoodDto> getFoods() {
    return nutriMapper.toFoodDtoList(foodRepository.findAll());
  }

  public FoodDto getFoodById(Long id) {
    var food =
        foodRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Could not find food with id: " + id));

    return nutriMapper.toDto(food);
  }

  public FoodDto createFood(FoodDto foodDto) {
    var food = foodRepository.saveAndFlush(nutriMapper.toFood(foodDto));

    return nutriMapper.toDto(food);
  }
}
