package com.ssafy.green.controller;


import com.ssafy.green.model.dto.DiaryRequest;
import com.ssafy.green.model.dto.DiaryResponse;
import com.ssafy.green.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public List<DiaryResponse> write(@RequestHeader("TOKEN") String token){
        logger.debug("# 토큰정보 {}: " + token);
        return diaryService.findAll(token);
    }
    /**
     * 다이어리 ID 조회!!
     */
    @ApiOperation(value = "다이어리 ID 조회!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- {id}(PathVariable): 다이어리 아이디\n\n" +
            "Response\n" +
            "- id\n" +
            "- plantId\n" +
            "- diaryTitle\n" +
            "- diaryContent\n" +
            "- imgs\n" +
            "- nickname\n" +
            "- writeDateTime\n")
    @GetMapping("/find/{id}")
    public DiaryResponse write(@RequestHeader("TOKEN") String token, @PathVariable Long id){
        logger.debug("# 토큰정보 {}: " + token);
        return diaryService.findById(id);
    }

    /**
     * 다이어리 작성!
     */
    @ApiOperation(value = "다이어리 작성!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- plantId\n" +
            "- diaryTitle\n" +
            "- diaryContent\n" +
            "- imgs (List)\n\n" +
            "Response\n" +
            "- success\n")
    @PostMapping("/write")
    public String write(@RequestHeader("TOKEN") String token, @RequestBody DiaryRequest diaryRequest){
        logger.debug("# 토큰정보 {}: " + token);
        diaryService.writeDiary(token, diaryRequest);
        return "success";
    }

    /**
     * 다이어리 수정!
     */
    @ApiOperation(value = "다이어리 수정!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- {id}(PathVariable): 다이어리 아이디\n" +
            "- diaryTitle\n" +
            "- diaryContent\n" +
            "- plantId\n" +
            "- imgs (List)\n\n" +
            "Response\n" +
            "- id\n" +
            "- plantId\n" +
            "- diaryTitle\n" +
            "- diaryContent\n" +
            "- imgs\n" +
            "- nickname\n" +
            "- writeDateTime\n")
    @PutMapping("/update/{id}")
    public DiaryResponse update(@RequestHeader("TOKEN") String token, @PathVariable Long id, @RequestBody DiaryRequest diaryRequest){
        logger.debug("# 토큰정보 {}: " + token);
        diaryService.update(token, id, diaryRequest);

        return  diaryService.findById(id);
    }

    /**
     * 다이어리 내용 삭제!
     */
    @ApiOperation(value = "다이어리 삭제!!", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- {id}(PathVariable): 다이어리 아이디\n\n" +
            "Response\n" +
            "- success\n"+
            "- fail\n")
    @PutMapping("/delete/{id}")
    public boolean delete(@RequestHeader("TOKEN") String token, @PathVariable Long id){
        logger.debug("# 토큰정보 {}: " + token);
        boolean result = diaryService.delete(token, id);
        return result;
    }

}
