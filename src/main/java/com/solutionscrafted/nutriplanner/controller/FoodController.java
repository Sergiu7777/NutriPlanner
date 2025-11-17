package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.dto.FoodDto;
import com.solutionscrafted.nutriplanner.service.FoodService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

  private final FoodService foodService;

  @GetMapping
  public ResponseEntity<List<FoodDto>> getAllFoods() {
    return ResponseEntity.ok(foodService.getFoods());
  }

  @GetMapping("/{id}")
  public ResponseEntity<FoodDto> getFood(@PathVariable Long id) {
    return ResponseEntity.ok(foodService.getFoodById(id));
  }

  @PostMapping
  public ResponseEntity<FoodDto> createFood(@RequestBody FoodDto request) {
    var food = foodService.createFood(request);
    return ResponseEntity.created(URI.create(String.format("/foods/%s", food.id()))).body(food);
  }
}
