package com.ssafy.green.controller;

import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ssafy.green.model.dto.MessageResponse;
import com.ssafy.green.model.dto.UserRequest;
import com.ssafy.green.model.dto.UserRequestV2;
import com.ssafy.green.model.dto.UserResponse;
import com.ssafy.green.model.entity.DeviceToken;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.service.RoomService;
import com.ssafy.green.service.UserService;
import com.ssafy.green.service.firebase.FirebaseCloudMessageService;
import com.ssafy.green.service.firebase.FirebaseInitService;
import com.ssafy.green.service.s3.S3Uploader;
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
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    public final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final S3Uploader s3Uploader;
    private final UserService userService;
    private final RoomService roomService;
    private final FirebaseInitService firebaseInit;
    private final FirebaseCloudMessageService fcmService;
    private final String DEFAULT_PROFILE_IMG = "https://ssafybucket.s3.ap-northeast-2.amazonaws.com/DEFAULT_PROFILE_IMG.png";


    @PostMapping("/sendMsg")
    @ApiOperation(value = "알림 전송")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestHeader("TOKEN") String idToken,
                              @RequestBody Message request){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            // 2. Device Token 조회!
            List<DeviceToken> allDeviceToken = userService.findAllDeviceToken(decodedToken.getUid());

            String messageKey = UUID.randomUUID().toString();

            // 3. 알림 전송
            for(DeviceToken d: allDeviceToken) {
                // 3-1. 알림 기록
                fcmService.sendMessageTo(d.getToken(), messageKey, request.getTitle(), request.getContent());
            }
            userService.recordMsg(decodedToken.getUid(), messageKey, request.getTitle(), request.getContent());

            resultMap.put("error", 0);
            resultMap.put("msg", "전송 성공!!");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", 1);
            resultMap.put("msg", "전송 실패!!");
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }
    @Data
    static class Message{
        private String title;
        private String content;
    }

    @ApiOperation(value = "알림 목록 조회!!")
    @GetMapping("/findAllMsg")
    public ResponseEntity<Map<String, Object>> findAllMessage(@RequestHeader("TOKEN") String idToken){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
          
            // 2. 알림 목록 조회
            List<MessageResponse> allMsg = userService.findAllMsg(decodedToken.getUid());
            resultMap.put("response", allMsg);
            resultMap.put("error", 0);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }
    @ApiOperation(value = "알림 확인 상태 변경!!(읽음 처리)")
    @PutMapping("/checkMsg")
    public ResponseEntity<Map<String, Object>> checkMessage(@RequestHeader("TOKEN") String idToken,
                                                            @RequestBody MessageReadRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            // 2. 알림 확인!!
            boolean result = userService.checkMsg(decodedToken.getUid(), request.getMessageKey());
            if(result) {
                List<MessageResponse> allMsg = userService.findAllMsg(decodedToken.getUid());
                resultMap.put("error", 0);
                resultMap.put("response", allMsg);
                resultMap.put("msg", "알림 확인 성공!!");
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            }
            resultMap.put("error", 1);
            resultMap.put("msg", "알림 확인 실패!!");
        }catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }

    @Data
    static class MessageReadRequest{
        private String messageKey;
    }



    /**
     * 디바이스 토큰 등록
     */
    @ApiOperation(value = "디바이스 토큰 등록!!")
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerToken(@RequestHeader("TOKEN") String idToken,
                                                             @RequestHeader("DEVICE") String deviceToken) {

        System.out.println("#### 디바이스 토큰 등록 api 호출!!!!");
        System.out.println("token : " + idToken);
        System.out.println("device : " + deviceToken);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            // 2. 토큰 등록
            boolean result = userService.registerToken(decodedToken.getUid(), deviceToken);
            if(result){
                resultMap.put("error", 0);
                resultMap.put("msg", "토큰 등록 성공!!");
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            }
            resultMap.put("error", 1);
            resultMap.put("msg", "토큰 등록 실패!!");
        } catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }

    /**
     * 디바이스 토큰 삭제
     */
    @ApiOperation(value = "Logout 시, 디바이스 토큰 삭제!!")
    @DeleteMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader("TOKEN") String idToken,
                                                             @RequestHeader("DEVICE") String deviceToken) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            // 2. 토큰 삭제
            boolean result = userService.deleteDeviceToken(decodedToken.getUid(), deviceToken);
            if(result){
                resultMap.put("error", 0);
                resultMap.put("msg", "디바이스 토큰 삭제 성공!!");
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            }
            resultMap.put("error", 1);
            resultMap.put("msg", "디바이스 토큰 삭제 실패!!");
        } catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }


    /**
     * 회원 정보 조회
     */
    @ApiOperation(value = "회원 정보 조회")
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
    @ApiOperation(value = "회원 정보 조회 V2")
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
     * 회원 정보 수정 v22222222
     */
    @ApiOperation(value = "회원 정보 수정 v2")
    @PutMapping("/updateInfo/v2")
    public ResponseEntity<Map<String, Object>> updateInfoV2(@RequestHeader("TOKEN") String idToken,
                                                          UserRequestV2 request) {
        logger.debug("# 토큰정보 {}: " + idToken);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);


            String fileName = DEFAULT_PROFILE_IMG;
            if(request.getProfile() != null){
                // 2. 이미지 업로드
                try {
                    fileName = s3Uploader.upload(request.getProfile());
                } catch (IOException e) {
                    e.printStackTrace();
                    resultMap.put("error", 1);
                    resultMap.put("msg", "파일 업로드 실패!!");
                    return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
                }
            }

            // 2. 회원 정보 수정
            UserResponse userResponse = userService.updateInfoV2(decodedToken.getUid(), request.getNickname(), fileName);
            resultMap.put("response", userResponse);
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
    @ApiOperation(value = "회원 정보 수정")
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

    @Data
    static class ThemaRequest {
        private String homeNickname;
        private String thema;
    }

    @ApiOperation(value = "홈 닉네임, 테마 변경")
    @PutMapping("/changeNickTheme")
    public ResponseEntity<Map<String, Object>> change(@RequestHeader("TOKEN") String idToken,
                                                      @RequestBody ThemaRequest request) {
        logger.debug("# 토큰정보 {}: " + idToken);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            resultMap.put("error", 0);
            userService.changeThema(decodedToken.getUid(), request.getThema(), request.getHomeNickname());
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

    @ApiOperation(value = "회원 정보 삭제")
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

    @Data
    static class ThemeHomeNickResponse {
        private String homeNickname;
        private String theme;
    }

    /**
     * 메인화면 ( 방 닉네임, 테마 보내주기)
     */
    @ApiOperation(value = "메인화면 조회")
    @GetMapping("/main")
    public ResponseEntity<ThemeHomeNickResponse> getMain(@RequestHeader("TOKEN") String idToken) {
        logger.debug("# 토큰정보 {}: " + idToken);
        Map<String, Object> resultMap = new HashMap<>();
        ThemeHomeNickResponse thnr=new ThemeHomeNickResponse();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            User user = userService.findUser(decodedToken.getUid());
            thnr.setHomeNickname(user.getHomeNickname());
            thnr.setTheme(user.getTheme());
        }catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
        }
        return new ResponseEntity<ThemeHomeNickResponse>(thnr, HttpStatus.OK);
    }


}
