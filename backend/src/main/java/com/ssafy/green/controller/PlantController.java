package com.ssafy.green.controller;

import com.ssafy.green.model.dto.plant.PlantListResponseDto;
import com.ssafy.green.model.dto.plant.PlantResponseDto;
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
    public List<PlantListResponseDto> findAllByCommonAndName(@PathVariable int category, @PathVariable String search) {
        List<PlantListResponseDto> list = null;
       if(category == 0) list = plantService.findAllByCommon(search);
       else if (category == 1) list = plantService.findAllByName(search);
       return list;
    }

    // 식물 상세 정보 조회
    @ApiOperation(value = "식물 상세 정보 조회", notes = "")
    @GetMapping("/info/{id}")
    public PlantResponseDto findByPlantInfo(@PathVariable Long id) {
        return plantService.findByPlantInfo(id);
    }
}
