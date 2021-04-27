package com.ssafy.green.controller;


import com.ssafy.green.model.dto.DiaryRequest;
import com.ssafy.green.model.dto.DiaryResponse;
import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    public final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final DiaryService diaryService;

    /**
     * 다이어리 전체 조회!!
     */
    @ApiOperation(value = "다이어리 전체 조회!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n\n" +
            "Response\n" +
            "- id\n" +
            "- plantId\n" +
            "- diaryTitle\n" +
            "- diaryContent\n" +
            "- imgs\n" +
            "- nickname\n" +
            "- writeDateTime\n")
    @GetMapping("/findAll")
    public ResponseEntity<Map<String, Object>> write(@RequestHeader("TOKEN") String token){
        logger.debug("# 토큰정보 {}: " + token);

        Map<String,Object> resultMap = new HashMap<>();
        List<DiaryResponse> allDiary = diaryService.findAll(token);

        resultMap.put("response", allDiary);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    /**
     * 다이어리 날짜 조회
     */
    @GetMapping("/findByDate/{date}")
    public ResponseEntity<Map<String, Object>> findByDate(@RequestHeader("TOKEN") String token, @PathVariable String date){
        Map<String,Object> resultMap = new HashMap<>();

        List<DiaryResponse> byDate = diaryService.findByDate(token, date);
        resultMap.put("response", byDate);

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }


    /**
     * 다이어리 ID 조회!!
     */
    @ApiOperation(value = "다이어리 ID 조회!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- {id}(PathVariable): 다이어리 아이디\n\n" +
            "Response\n" +
            "- id:  다이어리 아이디\n" +
            "- plantId: 식물 아이디\n" +
            "- content: 다이어리 내용\n" +
            "- imgUrls: 이미지 url 목록\n" +
            "- writeDateTime: 작성 날짜\n")
    @GetMapping("/find/{id}")
    public ResponseEntity<Map<String, Object>> write(@RequestHeader("TOKEN") String token, @PathVariable Long id){
        logger.debug("# 토큰정보 {}: " + token);
        Map<String,Object> resultMap = new HashMap<>();

        DiaryResponse findDiary = diaryService.findById(id);
        resultMap.put("response", findDiary);

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    /**
     * 다이어리 작성!
     */
    @ApiOperation(value = "다이어리 작성!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- plantId\n" +
            "- content\n" +
            "- imgs (List)\n\n" +
            "Response\n" +
            "- true : 성공\n"+
            "- false : 실패\n")
    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> write(@RequestHeader("TOKEN") String token, @RequestBody DiaryRequest diaryRequest){
        logger.debug("# 토큰정보 {}: " + token);
        Map<String,Object> resultMap = new HashMap<>();

        boolean result = diaryService.writeDiary(token, diaryRequest);
        resultMap.put("response", result);
        if(result){
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }

    /**
     * 다이어리 수정!
     */
    @ApiOperation(value = "다이어리 수정!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- {id}(PathVariable): 다이어리 아이디\n" +
            "- content: 내용\n" +
            "- plantId: 식물 아이디\n" +
            "- imgs (List)\n\n" +
            "Response\n" +
            "- id:  다이어리 아이디\n" +
            "- plantId: 식물 아이디\n" +
            "- content: 다이어리 내용\n" +
            "- imgUrls: 이미지 url 목록\n" +
            "- writeDateTime: 작성 날짜\n")
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@RequestHeader("TOKEN") String token, @PathVariable Long id, @RequestBody DiaryRequest diaryRequest){
        logger.debug("# 토큰정보 {}: " + token);
        Map<String,Object> resultMap = new HashMap<>();
        boolean result = diaryService.update(token, id, diaryRequest);
        if(result){
            DiaryResponse findDiary = diaryService.findById(id);
            resultMap.put("response", findDiary);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }

    /**
     * 다이어리 내용 삭제!
     */
    @ApiOperation(value = "다이어리 삭제!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- {id}(PathVariable): 다이어리 아이디\n\n" +
            "Response\n" +
            "- true\n"+
            "- false\n")
    @PutMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@RequestHeader("TOKEN") String token, @PathVariable Long id){
        logger.debug("# 토큰정보 {}: " + token);
        Map<String,Object> resultMap = new HashMap<>();
        boolean result = diaryService.delete(token, id);
        resultMap.put("response", result);
        if(result){
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }

}
