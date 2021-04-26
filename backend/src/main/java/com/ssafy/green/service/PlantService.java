package com.ssafy.green.service;

import com.ssafy.green.model.dto.plant.PlantListResponseDto;
import com.ssafy.green.model.dto.plant.PlantResponseDto;
import com.ssafy.green.model.entity.plant.PlantCare;
import com.ssafy.green.model.entity.plant.PlantInfo;
import com.ssafy.green.repository.PlantCareRepository;
import com.ssafy.green.repository.PlantInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantCareRepository plantCareRepository;
    private final PlantInfoRepository plantInfoRepository;

    // 식물 학명 조회
    @Transactional
    public List<PlantListResponseDto> findAllByCommon(String search) {
        return plantInfoRepository.findByCommonContaining(search).stream()
                .map(PlantListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 식물 이름 조회
    @Transactional
    public List<PlantListResponseDto> findAllByName(String search) {
        return plantInfoRepository.findByNameContaining(search).stream()
                .map(PlantListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 식물 상세정보 조회
    @Transactional
    public PlantResponseDto findByPlantInfo(Long id) {
        PlantInfo plantInfo = plantInfoRepository.findById(id).get();
        return new PlantResponseDto(plantInfo);
    }

    // 각 함수마다 식물 존재 확인
    private PlantCare getOne(Long id) throws IllegalArgumentException {
        return plantCareRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 식물이 없습니다.[id=" + id + "]"));
    }
}
