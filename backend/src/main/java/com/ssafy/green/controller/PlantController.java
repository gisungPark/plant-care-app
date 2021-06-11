package com.ssafy.green.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ssafy.green.model.dto.NoticeResponse;
import com.ssafy.green.model.dto.plant.*;
import com.ssafy.green.model.entity.plant.PlantCare;
import com.ssafy.green.service.PlantService;
import com.ssafy.green.service.UserService;
import com.ssafy.green.service.firebase.FirebaseCloudMessageService;
import com.ssafy.green.service.s3.S3Uploader;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.schema.Entry;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/plant")
@CrossOrigin("*")
public class PlantController {

    @Autowired
    private PlantService plantService;
    private final S3Uploader s3Uploader;
    private final UserService userService;
    private final FirebaseCloudMessageService fcmService;
    private final String DEFAULT_PlANT_IMAGE = "https://i.pinimg.com/564x/3e/93/03/3e9303d2646cb2d84fbb763f7eedb409.jpg";

    // 모든 식물 이름 조회
    @ApiOperation(value = "모든 식물 이름 조회(autocomplete를 위한 API)")
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
    @ApiOperation(value = "식물 이름 조회")
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

    // 식물 학명 조회
    @ApiOperation(value = "식물 학명 조회")
    @GetMapping("/check/{common}")
    public PlantResponse findByCommon(@RequestHeader("TOKEN") String token, @PathVariable String common) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.findByCommon(decodedToken.getUid(), common);
        } catch (FirebaseAuthException e) {

        }
        return null;
    }

    // 식물 상세 정보 조회
    @ApiOperation(value = "식물 상세 정보 조회")
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
    @ApiOperation(value = "나의 식물 조회 기반 등록")
    @PostMapping("/care")
    public Long saveBySearch(@RequestHeader("TOKEN") String token, MyPlantRequest myPlantRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String image = "";
            if(myPlantRequest.getImage() == null){
                image = DEFAULT_PlANT_IMAGE;
            }else{
                MultipartFile file = myPlantRequest.getImage();
                image = s3Uploader.upload(file);
            }
            return plantService.saveBySearch(decodedToken.getUid(), myPlantRequest, image);
        } catch (FirebaseAuthException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    // 나의 식물 이미지 분류 기반 등록 : 식물 학명 포함 정보 필요
    @ApiOperation(value = "나의 식물 이미지 분류 기반 등록")
    @PostMapping("/care/{common}")
    public Long saveByIdentify(@RequestHeader("TOKEN") String token, @PathVariable String common, MyPlantRequest myPlantRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String image = "";
            if(myPlantRequest.getImage() == null){
                image = DEFAULT_PlANT_IMAGE;
            }else{
                MultipartFile file = myPlantRequest.getImage();
                image = s3Uploader.upload(file);
            }
            return plantService.saveByIdentify(decodedToken.getUid(), common, myPlantRequest, image);
        } catch (FirebaseAuthException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    // 나의 식물 상세 정보
    @ApiOperation(value = "나의 식물 상세 정보")
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
    @ApiOperation(value = "나의 식물 수정")
    @PutMapping("/care/{pid}")
    public Long update(@RequestHeader("TOKEN") String token, @PathVariable Long pid, MyPlantRequest myPlantRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            MultipartFile multipartFile = myPlantRequest.getImage();
            String image = s3Uploader.upload(multipartFile);
            return plantService.update( decodedToken.getUid(), pid, myPlantRequest, image);
        } catch (FirebaseAuthException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    // 나의 식물 삭제
    @ApiOperation(value = "나의 식물 삭제")
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
    @ApiOperation(value = "나의 식물 떠나감")
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
    @ApiOperation(value = "식물의 모든 물 준 날짜 조회")
    @GetMapping("/care/water/{pid}")
    public List<WaterResponse> getWater(@RequestHeader("TOKEN") String token, @PathVariable Long pid) {
        try{
            System.out.println("오잉?");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.getWater(decodedToken.getUid(), pid);
        } catch (FirebaseAuthException e) {

        }
        return null;
    }

    // 물 준 날짜 등록
    @ApiOperation(value = "물 준 날짜 등록")
    @PostMapping("/care/water")
    public Long saveWater(@RequestHeader("TOKEN") String token, @RequestBody WaterRequest waterRequest) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.saveWater(decodedToken.getUid(), waterRequest);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }

    // 물 준 날짜 취소(삭제)
    @ApiOperation(value = "물 준 날짜 취소")
    @DeleteMapping("/care/water/{wid}")
    public Long deleteWater(@RequestHeader("TOKEN") String token, @PathVariable Long wid) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return plantService.deleteWater(decodedToken.getUid(), wid);
        } catch (FirebaseAuthException e) {

        }
        return 0L;
    }

    // 물 주기 알람
    @GetMapping("/water")
    @Scheduled(cron = "00 00 12,16 * * ?") // 매일 12, 16시 마다 알람가도록
    public void waterNotice() throws IOException {
        List<NoticeResponse> todayWater = plantService.todayWater();
    }
}
