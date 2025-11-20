package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.mappers.NutriMapper;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final NutriMapper nutriMapper;

    public List<PlanDto> getPlansByClientId(Long id) {
        return nutriMapper.toPlanDtoList(planRepository.findByClient_Id(id));
    }
}
