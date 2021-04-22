package com.ssafy.green.service;

import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.model.entity.UserType;
import com.ssafy.green.repository.DiaryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DiaryServiceTest {

    @Autowired UserService userService;
    @Autowired DiaryService diaryService;
    @Autowired DiaryRepository diaryRepository;

    @Test
    @Rollback(false)
    public void 다이어리_작성_테스트() throws Exception{
        //given
        User newUser = User.builder()
                .userId("ssafy")
                .nickname("왕고구마")
                .password("1234")
                .provider(UserType.google)
                .providerId("google")
                .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                .build();

        // 1. 회원 가입
        boolean result = userService.join(newUser);
        User findUser = userService.findUser(newUser.getUserId());

        //when
        Diary newDiary = Diary.builder()
                .diaryName("오늘 날씨가 참 맑다")
                .diaryContent("오늘 날씨가 좋아서 꽃에 물을 줬다.")
                .user(findUser)
                .nickname(findUser.getNickname())
                .build();

        diaryRepository.save(newDiary);

        User changedUser = userService.findUser(newUser.getUserId());

        List<Diary> diarys = diaryService.findAll(newUser.getUserId());
        for(Diary d : diarys ){
            System.out.println(d.toString());
        }

        Assert.assertEquals(1, diarys.size());


        //then
     }

}