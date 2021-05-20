package com.ssafy.green.service;

import com.ssafy.green.model.dto.NoticeResponse;
import com.ssafy.green.model.dto.plant.*;
import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.model.entity.plant.PlantCare;
import com.ssafy.green.model.entity.plant.PlantInfo;
import com.ssafy.green.model.entity.plant.Water;
import com.ssafy.green.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final UserRepository userRepository;
    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final PlantCareRepository plantCareRepository;
    @Autowired
    private final PlantInfoRepository plantInfoRepository;
    @Autowired
    private final WaterRepository waterRepository;
    @Autowired
    private final DeviceTokenRepository deviceTokenRepository;
    
    // 모든 식물 조회
    public List<PlantListResponse> findAll(String userId) {
        User curUser = getUser(userId);
        return plantInfoRepository.findAll().stream()
                .map(PlantListResponse::new)
                .collect(Collectors.toList());
    }

    // 식물 이름 조회
    public List<PlantListResponse> findByName(String userId, String search) {
        User curUser = getUser(userId);
        return plantInfoRepository.findByNameContaining(search).stream()
                .map(PlantListResponse::new)
                .collect(Collectors.toList());
    }

    // 식물 학명 조회
    public PlantResponse findByCommon(String userId, String common) {
        User curUser = getUser(userId);
        Optional<PlantInfo> plantInfo = plantInfoRepository.findByCommon(common);
        return new PlantResponse(plantInfo.get());
    }

    // 식물 상세 정보 조회
    public PlantResponse findByPlantInfo(String userId, Long id) {
        User curUser = getUser(userId);
        PlantInfo plantInfo = plantInfoRepository.findById(id).get();
        return new PlantResponse(plantInfo);
    }

    // 나의 식물 조회 기반 등록
    @Transactional
    public Long saveBySearch(String userId, MyPlantRequest myPlantRequest, String image) {
        User curUser = getUser(userId);
        Optional<Room> room = roomRepository.findById(myPlantRequest.getRid());
        Optional<PlantInfo> plantInfo = plantInfoRepository.findById(myPlantRequest.getPid());

        if(!curUser.getId().equals(room.get().getUser().getId()))
            return 0L;
        return savePlant(myPlantRequest, room, plantInfo, image);
    }

    // 나의 식물 이미지 분류 기반 등록
    @Transactional
    public Long saveByIdentify(String userId, String common, MyPlantRequest myPlantRequest, String image) {
        User curUser = getUser(userId);
        Optional<Room> room = roomRepository.findById(myPlantRequest.getRid());
        Optional<PlantInfo> plantInfo = plantInfoRepository.findByCommon(common);

        if(!curUser.getId().equals(room.get().getUser().getId()))
            return 0L;
        return savePlant(myPlantRequest, room, plantInfo, image);
    }

    private Long savePlant(MyPlantRequest myPlantRequest, Optional<Room> room, Optional<PlantInfo> plantInfo, String image) {
        PlantCare plantCare = PlantCare.builder()
                .nickname(myPlantRequest.getNickname())
                .startedDate(myPlantRequest.getStartedDate())
                .name(plantInfo.get().getName())
                .water(plantInfo.get().getWater())
                .image(image)
                .build();

        plantCare.setPlantInfo(plantInfo.get());
        plantCare.setRoom(room.get());
        plantCareRepository.save(plantCare);

        return plantCare.getId();
    }

    // 나의 식물 상세 정보 조회
    public MyPlantResponse findById(String userId, Long id){
        User curUser = getUser(userId);

        PlantCare plantCare = getOne(id);
        Optional<Room> room = roomRepository.findById(plantCare.getRoom().getId());
        PlantInfo plantInfo = plantInfoRepository.findById(plantCare.getPlantInfo().getId()).get();

        if(!curUser.getId().equals(room.get().getUser().getId()))
            return null;
        return new MyPlantResponse(plantCare, plantInfo);
    }

    // 나의 식물 수정
    @Transactional
    public Long update(String userId, Long id, MyPlantRequest myPlantRequest, String image) {
        User curUser = getUser(userId);

        PlantCare plantCare = getOne(id);
        Optional<Room> room = roomRepository.findById(plantCare.getRoom().getId());
        if(!curUser.getId().equals(room.get().getUser().getId()))
            return 0L;

        Room updateRoom = roomRepository.findById(myPlantRequest.getRid()).get();
        plantCare.setNickname(myPlantRequest.getNickname());
        plantCare.setStartedDate(myPlantRequest.getStartedDate());
        plantCare.setRoom(updateRoom);
        plantCare.setImage(image);
        plantCareRepository.save(plantCare);

        return id;
    }
    // 나의 식물 삭제
    @Transactional
    public Long delete(String userId, Long id) {
        User curUser = getUser(userId);

        PlantCare plantCare = getOne(id);
        Optional<Room> room = roomRepository.findById(plantCare.getRoom().getId());
        if(!curUser.getId().equals(room.get().getUser().getId()))
            return 0L;

        plantCare.setFlag(false);
        plantCareRepository.save(plantCare);
        return id;
    }

    // 나의 식물 떠나감
    @Transactional
    public Long dead(String userId, Long id) {
        User curUser = getUser(userId);

        PlantCare plantCare = getOne(id);
        Optional<Room> room = roomRepository.findById(plantCare.getRoom().getId());
        if(!curUser.getId().equals(room.get().getUser().getId()))
            return 0L;

        plantCare.setDead(false);
        plantCareRepository.save(plantCare);
        return id;
    }

    // 물 준 날짜 조회
    public List<WaterResponse> getWater(String userId, Long id){
        User curUser = getUser(userId);
        PlantCare plantCare = getOne(id);

        Optional<Room> room = roomRepository.findById(plantCare.getRoom().getId());
        if(!curUser.getId().equals(room.get().getUser().getId()))
            return null;

        List<WaterResponse> list = new ArrayList<>();
        for(Water water : plantCare.getWaterList()){
            list.add(new WaterResponse(water));
        }
        return list;
    }

    // 물 준 날짜 등록
    @Transactional
    public Long saveWater(String userId, WaterRequest waterRequest) {
        User curUser = getUser(userId);

        Optional<PlantCare> plantCare = plantCareRepository.findById(waterRequest.getPid());
        PlantCare getPlantCare = plantCare.get();

        Optional<Room> room = roomRepository.findById(getPlantCare.getRoom().getId());
        if(!curUser.getId().equals(room.get().getUser().getId()))
            return 0L;

        Water water = Water.builder()
                .waterDate(waterRequest.getWaterDate()).build();
        water.setPlantCare(plantCare.get());
        waterRepository.save(water);

        Optional<Water> getWater = waterRepository.findTopByPlantCareOrderByWaterDateDesc(getPlantCare);
        getPlantCare.setLastDate(getWater.get().getWaterDate());
        plantCareRepository.save(getPlantCare);

        return water.getId();
    }

    // 물 준 날짜 삭제
    @Transactional
    public Long deleteWater(String userId, Long id) {
        User curUser = getUser(userId);

        Water water = waterRepository.findById(id).get();
        Optional<PlantCare> plantCare = plantCareRepository.findById(water.getPlantCare().getId());
        PlantCare getPlantCare = plantCare.get();

        Optional<Room> room = roomRepository.findById(getPlantCare.getRoom().getId());
        if(!curUser.getId().equals(room.get().getUser().getId()))
            return 0L;

        waterRepository.delete(water);

        Optional<Water> getWater = waterRepository.findTopByPlantCareOrderByWaterDateDesc(getPlantCare);
        getPlantCare.setLastDate(getWater.get().getWaterDate());
        plantCareRepository.save(getPlantCare);

        return id;
    }

    @Transactional
    public List<NoticeResponse> todayWater() {
        List<PlantCare> plantCareList = plantCareRepository.findAllByDeadAndFlag(true, true);
        List<NoticeResponse> todayList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");

        for(PlantCare plantCare : plantCareList){
            LocalDate last = LocalDate.parse(plantCare.getLastDate(), formatter);
            if(plantCare.getWater().equals("주 2회")){
                if(today.equals(last.plusDays(4))) {
                    addTodayWater(todayList, plantCare);
                }
            }else if(plantCare.getWater().equals("주 1회")){
                if(today.equals(last.plusWeeks(1))) {
                    addTodayWater(todayList, plantCare);
                }
            }else if(plantCare.getWater().equals("2주 1회")){
                if(today.equals(last.plusWeeks(2))) {
                    addTodayWater(todayList, plantCare);
                }
            }else if(plantCare.getWater().equals("한 달 1회")){
                if(today.equals(last.plusMonths(1))) {
                    addTodayWater(todayList, plantCare);
                }
            }
        }
        return todayList;
    }

    private void addTodayWater(List<NoticeResponse> todayList, PlantCare plantCare) {
        Optional<Room> room = roomRepository.findById(plantCare.getRoom().getId());
        Hibernate.initialize(room.get().getUser().getUserId());
        String userId = room.get().getUser().getUserId();
        String device = deviceTokenRepository.findByUser(room.get().getUser()).get().getToken();
        todayList.add(new NoticeResponse(userId, device, plantCare.getNickname()));
    }

    // 각 함수마다 사용자 존재 확인
    private User getUser(String userId) throws IllegalArgumentException {
        return userRepository.findByUserIdAndFlag(userId, true).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 없습니다.[id=" + userId + "]"));
    }

    // 각 함수마다 식물 존재 확인
    private PlantCare getOne(Long id) throws IllegalArgumentException {
        return plantCareRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 식물이 없습니다.[id=" + id + "]"));
    }
}
