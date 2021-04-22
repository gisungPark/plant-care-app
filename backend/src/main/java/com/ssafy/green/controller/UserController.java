package com.ssafy.green.controller;

import com.ssafy.green.model.dto.CallbackDto;
import com.ssafy.green.model.dto.UserInfoDto;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.model.entity.UserType;
import com.ssafy.green.repository.UserRepository;
import com.ssafy.green.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    public final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserRepository userRepository;
    private final String DEFALLT_IMG = "http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG";


    /**
     * 아이디 중복 여부 체크
     */
    @ApiOperation(value = "아이디 중복 체크", notes="Parameter\n" +
            "- userId: 아이디\n\n" +
            "Response\n" +
            "- true: 사용가능한 아이디\n" +
            "- false: 사용 불가능한 아이디")
    @GetMapping("/checkEmail")
    public boolean validateEmail(@RequestParam String userId){
        return userService.validateDuplicateUserId(userId);
    }

    /**
     * 닉네임 중복 여부 체크
     */
    @ApiOperation(value = "닉네임 중복 체크", notes="Parameter\n" +
                    "- nickname: 닉네임\n\n" +
                    "Response\n" +
                    "- true: 사용가능한 닉네임\n" +
                    "- false: 사용 불가능한 닉네임")
    @GetMapping("/checkNickname")
    public boolean validateNickname(@RequestParam String nickname){
        return userService.validateDuplicateNickname(nickname);
    }

    /**
     * 일반 회원가입
     */
    @ApiOperation(value = "일반 회원 가입", notes = "Parameter\n" +
            "- userId: 이메일\n" +
            "- nickname: 닉네임\n" +
            "- password: 비밀번호\n\n" +
            "Response\n" +
            "- success\n" +
            "- fail ")
    @PostMapping("/join")
    public String join(@RequestBody UserInfoDto userInfo){
        User newUser = User.builder()
                .userId(userInfo.getUserId())
                .nickname(userInfo.getNickname())
                .password(userInfo.getPassword())
                .provider(UserType.basic)
                .providerId("")
                .profile(DEFALLT_IMG)
                .build();
        try {
            boolean result = userService.join(newUser);
            if(result) logger.info("회원가입에 성공했습니다.!!!");
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    /**
     * 일반 회원 로그인
     */
    @ApiOperation(value = "로그인", notes="Parameter\n" +
                    "- userId: 아이디\n" +
                    "- password: 비밀번호\n\n" +
                    "Response\n" +
                    "- token: 엑세스 토큰\n" +
                    "- userId: 유저 아이디\n" +
                    "- nickname: 닉네임\n" +
                    "- profile: 프로필 이미지\n" +
                    "- code: 0[로그인 성공], 1[로그인 실패]")
    @PostMapping("/login")
    public CallbackDto login(@RequestBody UserInfoDto userInfoDto){
        logger.debug("# 로그인 정보 {}: " + userInfoDto.toString());
        CallbackDto loginCallback = userService.login(userInfoDto);
        return loginCallback;
    }

    /**
     * 회원 정보 수정
     */
    @ApiOperation(value = "회원 정보 수정",
            notes = "Parameter\n" +
                    "- token(RequestHeader): 액세스 토큰\n" +
                    "- userId: 기존 회원아이디(변경 x, 필수 x)\n" +
                    "- nickname: 변경할 닉네임\n" +
                    "- password: 변경할 비밀번호\n" +
                    "- profile: 변경할 프로필 이미지\n\n" +
                    "Response\n" +
                    "- token: 엑세스 토큰\n" +
                    "- userId: 유저 아이디\n" +
                    "- nickname: 변경된 닉네임\n" +
                    "- profile: 변경된 프로필 이미지\n" +
                    "- code: (쓸데없음)")
    @PostMapping("/updateInfo")
    public CallbackDto updateInfo(@RequestHeader("TOKEN") String token, @RequestBody UserInfoDto userInfo) {
        logger.debug("# 토큰정보 {}: " + token);
        CallbackDto callbackDto = userService.updateInfo(token, userInfo);
        return callbackDto;
    }
}
