package com.ssafy.green.controller;

import com.google.firebase.auth.*;
import com.ssafy.green.model.dto.UserRequest;
import com.ssafy.green.model.dto.UserResponse;
import com.ssafy.green.service.UserService;
import com.ssafy.green.service.firebase.FirebaseInitService;
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
    private final FirebaseInitService firebaseInit;

    /**
     * 회원 정보 조회
     */
    @ApiOperation(value = "회원 정보 조회", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "Response\n" +
            "- userId: 유저 아이디\n" +
            "- nickname: 닉네임\n" +
            "- profile: 프로필 이미지\n" +
            "- error: 0[성공], 1[실패]")
    @PostMapping("/oauth")
    public ResponseEntity<Map<String, Object>> findUserInfo(@RequestHeader("TOKEN") String idToken) {
        logger.debug("# 토큰정보 {}: " + idToken);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            resultMap.put("error", 0);
            // 2. 회원 정보 조회
            resultMap.put("response", userService.oauthLogin(decodedToken.getUid()));
        } catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    /**
     * 회원 정보 수정
     */
    @ApiOperation(value = "회원 정보 수정",
            notes = "Parameter\n" +
                    "- token(RequestHeader) : Firebase token\n" +
                    "- nickname: 변경할 닉네임\n" +
                    "- profile: 변경할 프로필 이미지\n\n" +
                    "Response\n" +
                    "- userId: 유저 아이디\n" +
                    "- nickname: 변경된 닉네임\n" +
                    "- profile: 변경된 프로필 이미지\n" +
                    "- error: 0[성공], 1[실패]")
    @PutMapping("/updateInfo")
    public ResponseEntity<Map<String, Object>> updateInfo(@RequestHeader("TOKEN") String idToken,
                                                          @RequestBody UserRequest request) {
        logger.debug("# 토큰정보 {}: " + idToken);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            resultMap.put("error", 0);

            // 2. 회원 정보 수정
            UserResponse userResponse = userService.updateInfo(decodedToken.getUid(), request);
            resultMap.put("response", userResponse);
        } catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "테마 변경",
            notes = "Parameter\n" +
                    "- token(RequestHeader) : Firebase token\n" +
                    "- thema: 변경할 테마\n\n" +
                    "Response\n" +
                    "- error: 0[성공], 1[실패]")
    @PutMapping("/changeThema")
    public ResponseEntity<Map<String, Object>> change(@RequestHeader("TOKEN") String idToken,
                                                      @RequestBody ThemaRequest request) {
        logger.debug("# 토큰정보 {}: " + idToken);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            resultMap.put("error", 0);
            userService.changeThema(decodedToken.getUid(), request.getThema());
        } catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @Data
    static class ThemaRequest {
        private String thema;
    }


    @ApiOperation(value = "회원 정보 삭제",
            notes = "Parameter\n" +
                    "- token(RequestHeader) : Firebase token\n" +
                    "Response\n" +
                    "- error: 0[성공], 1[실패]")
    @PutMapping("/delete")
    public ResponseEntity<Map<String, Object>> change(@RequestHeader("TOKEN") String idToken) {
        logger.debug("# 토큰정보 {}: " + idToken);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            boolean result = userService.deleteUser(decodedToken.getUid());
            if (result) resultMap.put("error", 0);
            else {
                resultMap.put("error", 1);
                resultMap.put("msg", "존재하지 않는 회원입니다.");
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
            }
        } catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

}
