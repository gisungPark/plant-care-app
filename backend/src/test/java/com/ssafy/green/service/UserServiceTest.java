package com.ssafy.green.service;

import com.ssafy.green.model.dto.DiaryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    DiaryService diaryService;


    @Test
    public void 날짜조회() throws Exception{
        //given
        List<DiaryResponse> findAll = diaryService.findByDate("mQeyrQyVLCVgxzbscAhXKXosHqv1", "2021-05-06");

        for(DiaryResponse d : findAll){
            System.out.println(d.toString());
        }

        //when

        //then
     }



}