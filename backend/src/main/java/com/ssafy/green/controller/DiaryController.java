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

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    public final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final DiaryService diaryService;


    @PostMapping("/write")
    public String write(@RequestHeader("TOKEN") String token, @RequestBody DiaryDto diaryDto){
        logger.debug("# 토큰정보 {}: " + token);
        diaryService.writeDiary(token, diaryDto);
        return "success";
    }

    @GetMapping("/findAll")
    public List<Diary> write(@RequestHeader("TOKEN") String token){
        logger.debug("# 토큰정보 {}: " + token);
        return diaryService.findAll(token);
    }
}
