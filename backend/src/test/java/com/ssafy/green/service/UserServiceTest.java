package com.ssafy.green.service;

import com.ssafy.green.model.entity.User;
import com.ssafy.green.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    EntityManager em;



    @Test
    public void 회원가입() throws Exception {
        //given
        User newUser = User.builder()
                .userId("ssafy")
                .nickname("왕왕고구마")
                .password("1234")
                .provider("google")
                .providerId("google")
                .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                .build();
        //when
        boolean result = userService.join(newUser);

        //then
        Assert.assertEquals(true, result);
    }


    @Test(expected = IllegalStateException.class)
    public void 아이디_중복체크() throws Exception {
        //given
        User newUser = User.builder()
                .userId("ssafy")
                .nickname("왕왕고구마")
                .password("1234")
                .provider("google")
                .providerId("google")
                .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                .build();

        User newUser2 = User.builder()
                .userId("ssafy")
                .nickname("휴식공간입니다.")
                .password("1234")
                .provider("google")
                .providerId("google")
                .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                .build();
        //when
        userService.join(newUser);
        userService.join(newUser2);
        //then
        Assert.fail("아이디 중복체크 실패!");

    }

    @Test(expected = IllegalStateException.class)
    public void 닉네임_중복체크() throws Exception {
        //given
        User newUser = User.builder()
                .userId("parkssafy")
                .nickname("왕왕고구마")
                .password("1234")
                .provider("google")
                .providerId("google")
                .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                .build();

        User newUser2 = User.builder()
                .userId("kimssafy")
                .nickname("왕왕고구마")
                .password("1234")
                .provider("google")
                .providerId("google")
                .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                .build();
        //when
        userService.join(newUser);
        userService.join(newUser2);
        //then
        Assert.fail("닉네임 중복체크 실패!");

    }

    @Test
    public void 로그인_테스트1() throws Exception{
        //given
        User newUser = User.builder()
                .userId("ssafy")
                .nickname("왕고구마")
                .password("1234")
                .provider("google")
                .providerId("google")
                .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                .build();
        //when
        userService.join(newUser);
        User findUser = userService.login(newUser);

        //then
        Assert.assertEquals(newUser.getNickname(), findUser.getNickname());
     }
}