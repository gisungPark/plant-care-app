package com.ssafy.green.controller;

import com.ssafy.green.model.dto.plant.MyPlantRequest;
import com.ssafy.green.model.dto.plant.MyPlantResponse;
import com.ssafy.green.model.dto.plant.PlantListResponse;
import com.ssafy.green.model.dto.plant.PlantResponse;
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

    // 식물 학명 또는 이름 조회
    @ApiOperation(value = "식물 학명 또는 이름 조회", notes = "[category] 0: 학명으로 조회, 1: 이름으로 조회 & "+
                                                        "[search] 검색할 키워드")
    @GetMapping("/info/{category}/{search}")
    public List<PlantListResponse> findAllByCommonAndName(@PathVariable int category, @PathVariable String search) {
        List<PlantListResponse> list = null;
       if(category == 0) list = plantService.findAllByCommon(search);
       else if (category == 1) list = plantService.findAllByName(search);
       return list;
    }

    // 식물 상세 정보 조회
    @ApiOperation(value = "식물 상세 정보 조회", notes = "")
    @GetMapping("/info/{id}")
    public PlantResponse findByPlantInfo(@PathVariable Long id) {
        return plantService.findByPlantInfo(id);
    }

    // 나의 식물 검색 기반 등록 : 식물 pid 포함 정보 필요
    @ApiOperation(value = "나의 식물 검색 기반 등록", notes = "식물 pid을 포함한 정보 필요")
    @PostMapping("/care")
    public Long save(@RequestBody MyPlantRequest myPlantRequest) { return plantService.saveBySearch(myPlantRequest); }

    // 나의 식물 이미지 분류 기반 등록 : 식물 학명 포함 정보 필요
    @ApiOperation(value = "나의 식물 이미지 분류 기반 등록", notes = "식물 학명을 포함한 정보 필요 / pid = 0으로 보내주세요!")
    @PostMapping("/care/{common}")
    public Long save(@PathVariable String common, @RequestBody MyPlantRequest myPlantRequest) { return plantService.saveByIdentify(common, myPlantRequest); }

    // 나의 식물 상세 정보
    @ApiOperation(value = "나의 식물 상세 정보")
    @GetMapping("/care/{pid}")
    public MyPlantResponse findById(@PathVariable Long pid) {
        return plantService.findById(pid);
    }

    // 나의 식물 수정
    @ApiOperation(value = "나의 식물 수정")
    @PutMapping("/care/{pid}")
    public Long update(@PathVariable Long pid, @RequestBody MyPlantRequest myPlantRequest) {
        return plantService.update(pid, myPlantRequest);
    }

    // 나의 식물 삭제
    @ApiOperation(value = "나의 식물 삭제")
    @PutMapping("/care/delete/{pid}")
    public Long delete(@PathVariable Long pid) {
        return plantService.delete(pid);
    }

    // 나의 식물 떠나감
    @ApiOperation(value = "나의 식물 떠나감")
    @PutMapping("/care/dead/{pid}")
    public Long dead(@PathVariable Long pid) {
        return plantService.dead(pid);
    }
}
