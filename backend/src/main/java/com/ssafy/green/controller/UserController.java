package com.ssafy.green.controller;

import com.ssafy.green.service.UserService;
import com.ssafy.green.service.firebase.FirebaseCloudMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    public final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    /**
     * 소셜 로그인
     */
    @ApiOperation(value = "구글 로그인", notes="Parameter\n" +
            "- userId: Firebase uid\n" +
            "Response\n" +
            "- userId: 유저 아이디\n" +
            "- nickname: 닉네임\n" +
            "- profile: 프로필 이미지\n" +
            "- code: 0[로그인 성공], 1[로그인 실패]")
    @PostMapping("/oauth")
    public ResponseEntity<Map<String, Object>> oauthLogin(@RequestBody OauthRequest oauth){
        logger.debug("# 로그인 정보 {}: " + oauth);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("response", userService.oauthLogin(oauth.getUserId()));

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @Data
    static class OauthRequest{
        private String userId;
    }

//    /**
//     * 일반 회원가입
//     */
//    @ApiOperation(value = "일반 회원 가입", notes = "Parameter\n" +
//            "- userId: 이메일\n" +
//            "- nickname: 닉네임\n" +
//            "- password: 비밀번호\n\n" +
//            "Response\n" +
//            "- success\n" +
//            "- fail ")
//    @PostMapping("/join")
//    public ResponseEntity<Map<String, Object>> join(@RequestBody UserRequest userRequest){
//        Map<String,Object> resultMap = new HashMap<>();
//        try {
//            boolean result = userService.join(userRequest);
//            if(result) {
//                resultMap.put("response", result);
//                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
//            }
//            resultMap.put("response", "false");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
//    }
//
//    /**
//     * 일반 회원 로그인
//     */
//    @ApiOperation(value = "로그인", notes="Parameter\n" +
//                    "- userId: 아이디\n" +
//                    "- password: 비밀번호\n\n" +
//                    "Response\n" +
//                    "- token: 엑세스 토큰\n" +
//                    "- userId: 유저 아이디\n" +
//                    "- nickname: 닉네임\n" +
//                    "- profile: 프로필 이미지\n" +
//                    "- code: 0[로그인 성공], 1[로그인 실패]")
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody UserRequest userRequest){
//        logger.debug("# 로그인 정보 {}: " + userRequest.toString());
//        Map<String,Object> resultMap = new HashMap<>();
//
//        UserResponse userResponse = userService.login(userRequest);
//        resultMap.put("response", userResponse);
//
//        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
//    }

//    /**
//     * 회원 정보 수정
//     */
//    @ApiOperation(value = "회원 정보 수정",
//            notes = "Parameter\n" +
//                    "- token(RequestHeader): 액세스 토큰\n" +
//                    "- userId: 기존 회원아이디(변경 x, 필수 x)\n" +
//                    "- nickname: 변경할 닉네임\n" +
//                    "- password: 변경할 비밀번호\n" +
//                    "- profile: 변경할 프로필 이미지\n\n" +
//                    "Response\n" +
//                    "- token: 엑세스 토큰\n" +
//                    "- userId: 유저 아이디\n" +
//                    "- nickname: 변경된 닉네임\n" +
//                    "- profile: 변경된 프로필 이미지\n" +
//                    "- code: (쓸데없음)")
//    @PutMapping("/updateInfo")
//    public ResponseEntity<Map<String, Object>> updateInfo(@RequestHeader("TOKEN") String token, @RequestBody UserRequest userRequest) {
//        logger.debug("# 토큰정보 {}: " + token);
//        Map<String,Object> resultMap = new HashMap<>();
//
//        UserResponse userResponse = userService.updateInfo(token, userRequest);
//        resultMap.put("response", userResponse);
//
//        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
//    }


}
