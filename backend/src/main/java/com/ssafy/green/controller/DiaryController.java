package com.ssafy.green.controller;


import com.ssafy.green.model.dto.DiaryDto;
import com.ssafy.green.model.dto.UserInfoDto;
import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.repository.UserRepository;
import com.ssafy.green.service.DiaryService;
import com.ssafy.green.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    public final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final DiaryService diaryService;

    /**
     * 다이어리 전체 조회!!
     */
    @GetMapping("/findAll")
    public List<Diary> write(@RequestHeader("TOKEN") String token){
        logger.debug("# 토큰정보 {}: " + token);
        return null;
    }

    /**
     * 다이어리 작성!
     */
    @PostMapping("/write")
    public String write(@RequestHeader("TOKEN") String token, @RequestBody DiaryDto diaryDto){
        logger.debug("# 토큰정보 {}: " + token);
        diaryService.writeDiary(token, diaryDto);
        return "success";
    }

    /**
     * 다이어리 수정!
     */
    @PutMapping("/update")
    public String update(@RequestHeader("TOKEN") String token, @RequestBody DiaryDto diaryDto){
        logger.debug("# 토큰정보 {}: " + token);
        diaryService.update(token, diaryDto);
        return "success";
    }

    /**
     * 다이어리 내용 삭제!
     */

}
