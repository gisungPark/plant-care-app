package com.ssafy.green.controller;

import com.ssafy.green.model.dto.CallbackDto;
import com.ssafy.green.model.dto.JoinDto;
import com.ssafy.green.model.dto.UserInfoDto;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.model.entity.UserType;
import com.ssafy.green.repository.UserRepository;
import com.ssafy.green.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final String DEFALLT_IMG = "http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG";


    /**
     * 이메일 중복 여부 체크
     */
    @GetMapping("/checkEmail")
    public boolean validateEmail(@RequestParam String userId){
        return userService.validateDuplicateUserId(userId);
    }

    /**
     * 닉네임 중복 여부 체크
     */
    @GetMapping("/checkNickname")
    public boolean validateNickname(@RequestParam String nickname){
        return userService.validateDuplicateNickname(nickname);
    }

    /**
     * 일반 회원가입
     */
    @PostMapping("/join")
    public User join(@RequestBody JoinDto joinDto){
        User newUser = User.builder()
                .userId(joinDto.getUserId())
                .nickname(joinDto.getNickname())
                .password(joinDto.getPassword())
                .provider(UserType.basic)
                .providerId("")
                .profile(DEFALLT_IMG)
                .build();

        try {
            boolean result = userService.join(newUser);
            if(result) System.out.println("회원가입에 성공했습니다. ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userService.findUser(newUser.getUserId());
    }

    /**
     * 일반 회원 로그인
     */
    @PostMapping("/login")
    public CallbackDto login(@RequestBody UserInfoDto userInfoDto){

        System.out.println(userInfoDto);
        CallbackDto loginCallback = userService.login(userInfoDto);
        return loginCallback;
    }


    /**
     * 회원 정보 수정
     */
    @PostMapping("/updateInfo")
    public void updateInfo(@RequestParam UserInfoDto userInfo) {
    }
}
