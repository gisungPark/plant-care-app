package com.ssafy.green.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ssafy.green.model.dto.plant.*;
import com.ssafy.green.service.PlantService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/plant")
@CrossOrigin("*")
public class PlantController {

    @Autowired
    private PlantService plantService;

    // 식물 이름 조회
    @ApiOperation(value = "모든 식물 이름 조회(autocomplete를 위한 API)", notes =
            "Response\n" +
            "- id : 식물 정보 아이디 \n" +
            "- common : 식물 학명 \n" +
            "- name : 식물 이름 \n\n" +
            "값 없을 때(null) : 토큰 검사 실패한 경우 ")
    @GetMapping("/info")
    public List<PlantListResponse> findAll(@RequestHeader("TOKEN") String token) {
        List<PlantListResponse> list = null;
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            list = plantService.findAll(decodedToken.getUid());
        } catch (FirebaseAuthException e) {

        }
        return list;
    }

    // 식물 이름 조회
    @ApiOperation(value = "식물 이름 조회", notes =
            "Path\n" +
            "- search : 조회할 키워드\n\n"+
            "Response\n" +
            "- id : 식물 정보 아이디 \n" +
            "- common : 식물 학명 \n" +
            "- name : 식물 이름 \n\n" +
            "값 없을 때(null) : 토큰 검사 실패한 경우 ")
    @GetMapping("/info/{search}")
    public List<PlantListResponse> findAllByCommonAndName(@RequestHeader("TOKEN") String token, @PathVariable String search){
        List<PlantListResponse> list = null;
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            list = plantService.findByName(decodedToken.getUid(), search);
        } catch (FirebaseAuthException e) {

        }
        return list;
    }

    // 식물 상세 정보 조회
    @ApiOperation(value = "식물 상세 정보 조회", notes =
            "Path\n" +
            "- pid : 식물 아이디(식물 조회 후)\n"+
            "Response\n" +
            "- id : 식물 정보 아이디 \n" +
            "- common : 식물 학명 \n" +
            "- name : 식물 이름 \n" +
            "- level : 키우기 난이도 \n" +
            "- temp : 적정 온도 \n" +
            "- humid : 적정 습도 \n" +
            "- water : 물주기 \n" +
            "- info : 식물 정보 \n" +
            "- image : 식물 이미지 \n\n" +
            "값 없을 때(null) : 토큰 검사 실패한 경우 ")
    @GetMapping("/info/detail/{id}")
    public PlantResponse findByPlantInfo(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.findByPlantInfo(decodedToken.getUid(), id);
        } catch (FirebaseAuthException e) {

        }
        return null;
    }

    // 나의 식물 조회 기반 등록 : 식물 pid 포함 정보 필요
    @ApiOperation(value = "나의 식물 조회 기반 등록", notes =
            "Request\n" +
            "- pid : 식물 아이디(식물 조회 후)\n"+
            "- rid : 식물을 등록할 방 아이디\n"+
            "- nickname : 식물 애칭\n"+
            "- startedDate : 식물 키우기 시작한 날짜\n\n"+
            "Response\n" +
            "- 1 이상 : 등록 성공한 나의 식물 아이디 \n" +
            "- 0 : 등록 실패")
    @PostMapping("/care")
    public Long saveBySearch(@RequestHeader("TOKEN") String token, @RequestBody MyPlantRequest myPlantRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.saveBySearch(decodedToken.getUid(), myPlantRequest);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }

    // 나의 식물 이미지 분류 기반 등록 : 식물 학명 포함 정보 필요
    @ApiOperation(value = "나의 식물 이미지 분류 기반 등록", notes =
            "Path\n" +
            "- common : 식물 학명(식물 이미지 분류 후 얻은)\n\n" +
            "Request\n" +
            "- pid : 0\n"+
            "- rid : 식물을 등록할 방 아이디\n"+
            "- nickname : 식물 애칭\n"+
            "- startedDate : 식물 키우기 시작한 날짜\n\n"+
            "Response\n" +
            "- 1 이상 : 등록 성공한 나의 식물 아이디 \n" +
            "- 0 : 등록 실패")
    @PostMapping("/care/{common}")
    public Long saveByIdentify(@RequestHeader("TOKEN") String token, @PathVariable String common, @RequestBody MyPlantRequest myPlantRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.saveByIdentify(decodedToken.getUid(), common, myPlantRequest);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }

    // 나의 식물 상세 정보
    @ApiOperation(value = "나의 식물 상세 정보", notes =
            "Path\n" +
            "- pid : 나의 식물 아이디 \n\n"+
            "Response\n" +
            "- pid : 나의 식물 아이디 \n" +
            "- nickname : 나의 식물 애칭 \n" +
            "- startedDate : 키우기 시작 날짜 \n" +
            "- lastDate : 마지막으로 물 준 날짜 \n" +
            "- image : 식물 이미지 \n" +
            "- common : 식물 학명 \n" +
            "- name : 식물 이름 \n" +
            "- water : 물주기 \n" +
            "- temp : 적정 온도 \n" +
            "- humid : 적정 습도 \n" +
            "- info : 식물 정보 \n\n" +
            "값 없을 때(null) : 토큰 검사 실패하였거나 나의 식물이 아닐 경우")
    @GetMapping("/care/{pid}")
    public MyPlantResponse findById(@RequestHeader("TOKEN") String token, @PathVariable Long pid) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.findById(decodedToken.getUid(), pid);
        } catch (FirebaseAuthException e) {

        }
        return null;
    }

    // 나의 식물 수정
    @ApiOperation(value = "나의 식물 수정", notes =
            "Path\n" +
            "- pid : 나의 식물 아이디\n\n" +
            "Request\n" +
            "- pid : 나의 식물 아이디\n"+
            "- rid : 수정할 식물을 등록할 방 아이디\n"+
            "- nickname : 수정할 식물 애칭\n"+
            "- startedDate : 수정할 키우기 시작한 날짜\n\n"+
            "Response\n" +
            "- 1 이상 : 수정 성공한 나의 식물 아이디 \n" +
            "- 0 : 수정 실패(토큰 검사 실패 or 나의 식물이 아닐 경우)")
    @PutMapping("/care/{pid}")
    public Long update(@RequestHeader("TOKEN") String token, @PathVariable Long pid, @RequestBody MyPlantRequest myPlantRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.update(decodedToken.getUid(), pid, myPlantRequest);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }

    // 나의 식물 삭제
    @ApiOperation(value = "나의 식물 삭제", notes =
            "Path\n" +
            "- pid : 나의 식물 아이디\n\n" +
            "Response\n" +
            "- 1 이상 : 삭제 성공한 나의 식물 아이디 \n" +
            "- 0 : 삭제 실패(토큰 검사 실패 or 나의 식물이 아닐 경우)")
    @PutMapping("/care/delete/{pid}")
    public Long delete(@RequestHeader("TOKEN") String token, @PathVariable Long pid) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.delete(decodedToken.getUid(), pid);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }

    // 나의 식물 떠나감
    @ApiOperation(value = "나의 식물 떠나감", notes =
            "Path\n" +
            "- pid : 나의 식물 아이디\n\n" +
            "Response\n" +
            "- 1 이상 : 떠나감 성공한 나의 식물 아이디 \n" +
            "- 0 : 떠나감 실패(토큰 검사 실패 or 나의 식물이 아닐 경우)")
    @PutMapping("/care/dead/{pid}")
    public Long dead(@RequestHeader("TOKEN") String token, @PathVariable Long pid) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.dead(decodedToken.getUid(), pid);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }

    // 물 준 날짜 조회
    @ApiOperation(value = "물 준 날짜 조회", notes = "식물 pid 필요")
    @GetMapping("/care/water/{pid}")
    public List<WaterResponse> getWater(@RequestHeader("TOKEN") String token, @PathVariable Long pid) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.getWater(decodedToken.getUid(), pid);
        } catch (FirebaseAuthException e) {

        }
        return null;
    }

    // 물 준 날짜 등록
    @ApiOperation(value = "물 준 날짜 등록", notes = "식물 pid, 날짜 필요")
    @PostMapping("/care/water")
    public Long saveWater(@RequestHeader("TOKEN") String token, @RequestBody WaterRequest waterRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.saveWater(decodedToken.getUid(), waterRequest);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }

    // 물 준 날짜 수정
    @ApiOperation(value = "물 준 날짜 수정", notes = "해당 wid / 수정할 날짜 필요")
    @PutMapping("/care/water/{wid}")
    public Long updateWater(@RequestHeader("TOKEN") String token, @PathVariable Long wid, @RequestBody WaterRequest waterRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.updateWater(decodedToken.getUid(), wid, waterRequest);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }
}
