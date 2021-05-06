package com.ssafy.green.controller;

import com.google.firebase.auth.*;
import com.ssafy.green.model.dto.RoomResponse;
import com.ssafy.green.model.dto.UserRequest;
import com.ssafy.green.model.dto.UserResponse;
import com.ssafy.green.service.RoomService;
import com.ssafy.green.service.UserService;
import com.ssafy.green.service.firebase.FirebaseCloudMessageService;
import com.ssafy.green.service.firebase.FirebaseInitService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    public final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoomService roomService;
    private final FirebaseInitService firebaseInit;
    private final FirebaseCloudMessageService fcmService;

    @GetMapping("/msg")
    public String sendMessage(){
        String deviceToken = "dtCQ7_dDTuuMa7-gfezDYA:APA91bGtKeWxFXk3iHwKGb0rHMxolUjjIetUHxcVf1y5expZc9PPa1jRPE5RW0ycrdsbCaE-MPE9ONGmw1RhBPV8CfvKl2DjTKf1UFc3joYbEYXmKn_TkfeDysemFsjXOmXYMJgVmixd";
        try {
            fcmService.sendMessageTo(deviceToken, "메세지 잘 갔나요?? ", "갔으면 대답좀 해주세요");
        } catch (IOException e) {
            e.printStackTrace();
            return "file";
        }
        return "success";
    }

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
     * 회원 정보 조회
     */
    @ApiOperation(value = "회원 정보 조회 V2", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "Response\n" +
            "- response.userId: 유저 아이디\n" +
            "- response.nickname: 닉네임\n" +
            "- response.profile: 프로필 이미지\n" +
            "- plants.pid: 식물 id\n" +
            "- plants.nickname: 식물 닉네임\n" +
            "- plants.name: 식물 이름\n" +
            "- plants.lastDate: ????\n" +
            "- plants.image: 식물 이미지\n" +
            "- error: 0[성공], 1[실패]")
    @PostMapping("/oauth/v2")
    public ResponseEntity<Map<String, Object>> findUserInfoV2(@RequestHeader("TOKEN") String idToken) {
        logger.debug("# 토큰정보 {}: " + idToken);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            // 2. 회원 정보 조회
            resultMap.put("response", userService.oauthLogin(decodedToken.getUid()));
            resultMap.put("plants", userService.findMyPlants(decodedToken.getUid()));
            resultMap.put("error", 0);
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
