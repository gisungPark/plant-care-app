package com.ssafy.green.service;

import com.ssafy.green.model.dto.plant.MyPlantRequest;
import com.ssafy.green.model.dto.plant.MyPlantResponse;
import com.ssafy.green.model.dto.plant.PlantListResponse;
import com.ssafy.green.model.dto.plant.PlantResponse;
import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.plant.PlantCare;
import com.ssafy.green.model.entity.plant.PlantInfo;
import com.ssafy.green.repository.PlantCareRepository;
import com.ssafy.green.repository.PlantInfoRepository;
import com.ssafy.green.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {
    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final PlantCareRepository plantCareRepository;
    @Autowired
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

    // 식물 상세 정보 조회
    public PlantResponse findByPlantInfo(Long id) {
        PlantInfo plantInfo = plantInfoRepository.findById(id).get();
        return new PlantResponse(plantInfo);
    }

    // 나의 식물 검색 기반 등록
    @Transactional
    public Long saveBySearch(MyPlantRequest myPlantRequest) {
        Optional<Room> room = roomRepository.findById(myPlantRequest.getRid());
        Optional<PlantInfo> plantInfo = plantInfoRepository.findById(myPlantRequest.getPid());

        return savePlant(myPlantRequest, room, plantInfo);
    }

    // 나의 식물 이미지 분류 기반 등록
    @Transactional
    public Long saveByIdentify(String common, MyPlantRequest myPlantRequest) {
        Optional<Room> room = roomRepository.findById(myPlantRequest.getRid());
        Optional<PlantInfo> plantInfo = plantInfoRepository.findByCommon(common);

        return savePlant(myPlantRequest, room, plantInfo);
    }

    private Long savePlant(MyPlantRequest myPlantRequest, Optional<Room> room, Optional<PlantInfo> plantInfo) {
        PlantCare plantCare = PlantCare.builder()
                .nickname(myPlantRequest.getNickname())
                .startedDate(myPlantRequest.getStartedDate())
                .name(plantInfo.get().getName())
                .water(plantInfo.get().getWater())
                .build();

        plantCare.setPlantInfo(plantInfo.get());
        plantCare.setRoom(room.get());
        plantCareRepository.save(plantCare);
        return plantCare.getId();
    }

    // 나의 식물 상세 정보 조회
    public MyPlantResponse findById(Long id){
        PlantCare plantCare = getOne(id);
        PlantInfo plantInfo = plantInfoRepository.findById(plantCare.getPlantInfo().getId()).get();
        return new MyPlantResponse(plantCare, plantInfo);
    }

    // 나의 식물 수정
    @Transactional
    public Long update(Long id, MyPlantRequest myPlantRequest) {
        PlantCare plantCare = getOne(id);
        Room room = roomRepository.findById(myPlantRequest.getRid()).get();

        plantCare.setNickname(myPlantRequest.getNickname());
        plantCare.setStartedDate(myPlantRequest.getStartedDate());
        plantCare.setRoom(room);
        plantCareRepository.save(plantCare);

        return id;
    }
    // 나의 식물 삭제
    @Transactional
    public Long delete(Long id) {
        PlantCare plantCare = getOne(id);
        plantCare.setFlag(false);
        plantCareRepository.save(plantCare);
        return id;
    }

    // 나의 식물 떠나감
    @Transactional
    public Long dead(Long id) {
        PlantCare plantCare = getOne(id);
        plantCare.setDead(false);
        plantCareRepository.save(plantCare);
        return id;
    }

    // 각 함수마다 식물 존재 확인
    private PlantCare getOne(Long id) throws IllegalArgumentException {
        return plantCareRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 식물이 없습니다.[id=" + id + "]"));
    }
}
