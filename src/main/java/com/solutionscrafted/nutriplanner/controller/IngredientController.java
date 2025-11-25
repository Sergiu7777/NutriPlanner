package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.controller.requestbody.IngredientRequestDto;
import com.solutionscrafted.nutriplanner.dto.IngredientDto;
import com.solutionscrafted.nutriplanner.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<Page<IngredientDto>> getIngredients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Get ingredients paginated request: /ingredients. Page: {}, Size: {}.", page, size);

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(ingredientService.getIngredients(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> getIngredient(@PathVariable Long id) {
        log.info("Get ingredient by id request: /ingredients/{}.", id);

        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientRequestDto request) {
        log.info("Create ingredient request: /ingredients. Body: {}.", request);

        var ingredient = ingredientService.createIngredient(request);
        return ResponseEntity.created(URI.create(String.format("/ingredients/%s", ingredient.id()))).body(ingredient);
    }

    @PostMapping("/{ingredient_id}")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable("ingredient_id") Long ingredientId, @RequestBody IngredientRequestDto requestDto) {
        log.info("Update ingredient request: /ingredients/{}. Body: {}.", ingredientId, requestDto);

        return ResponseEntity.ok(ingredientService.updateIngredient(ingredientId, requestDto));
    }

    @DeleteMapping("/{ingredient_id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable("ingredient_id") Long ingredientId) {
        log.info("Delete ingredient request: /ingredients/{}.", ingredientId);

        ingredientService.deleteIngredient(ingredientId);

        return ResponseEntity.accepted().build();
    }
}
