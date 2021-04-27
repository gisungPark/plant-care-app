package com.ssafy.green.service;

import com.ssafy.green.model.dto.plant.MyPlantRequest;
import com.ssafy.green.model.dto.plant.PlantListResponse;
import com.ssafy.green.model.dto.plant.PlantResponse;
import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.plant.PlantCare;
import com.ssafy.green.model.entity.plant.PlantInfo;
import com.ssafy.green.repository.PlantCareRepository;
import com.ssafy.green.repository.PlantInfoRepository;
import com.ssafy.green.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final RoomRepository roomRepository;
    private final PlantCareRepository plantCareRepository;
    private final PlantInfoRepository plantInfoRepository;

    // 식물 학명 조회
    @Transactional
    public List<PlantListResponse> findAllByCommon(String search) {
        return plantInfoRepository.findByCommonContaining(search).stream()
                .map(PlantListResponse::new)
                .collect(Collectors.toList());
    }

    // 식물 이름 조회
    @Transactional
    public List<PlantListResponse> findAllByName(String search) {
        return plantInfoRepository.findByNameContaining(search).stream()
                .map(PlantListResponse::new)
                .collect(Collectors.toList());
    }

    // 식물 상세정보 조회
    @Transactional
    public PlantResponse findByPlantInfo(Long id) {
        PlantInfo plantInfo = plantInfoRepository.findById(id).get();
        return new PlantResponse(plantInfo);
    }

    // 식물 등록
    @Transactional
    public Long save(MyPlantRequest myPlantRequest) {
        Room room = roomRepository.findById(myPlantRequest.getRid()).get();
        PlantInfo plantInfo = plantInfoRepository.findById(myPlantRequest.getPid()).get();

        PlantCare plantCare = PlantCare.builder()
                .nickname(myPlantRequest.getNickname())
                .started_date(myPlantRequest.getStarted_date())
                .name(plantInfo.getName())
                .water(plantInfo.getWater())
                .build();

        plantCare.setPlantInfo(plantInfo);
        plantCare.setRoom(room);
        plantCareRepository.save(plantCare);
        return plantCare.getId();
    }

    // 각 함수마다 식물 존재 확인
    private PlantCare getOne(Long id) throws IllegalArgumentException {
        return plantCareRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 식물이 없습니다.[id=" + id + "]"));
    }
}
