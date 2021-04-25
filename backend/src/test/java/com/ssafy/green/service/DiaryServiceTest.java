package com.ssafy.green.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DiaryServiceTest {

    @Autowired DiaryService diaryService;
    @Autowired UserService userService;

    @Test
    public void 다이어리_작성_테스트() throws Exception{

        //then
     }

}