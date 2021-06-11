package com.ssafy.green.controller;


import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ssafy.green.model.dto.DiaryRequestV2;
import com.ssafy.green.model.dto.DiaryRequestV3;
import com.ssafy.green.model.dto.DiaryResponse;
import com.ssafy.green.service.DiaryService;
import com.ssafy.green.service.s3.S3Uploader;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    public final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final DiaryService diaryService;
    private final S3Uploader s3Uploader;

    /**
     * 다이어리 전체 조회!!
     */
    @ApiOperation(value = "다이어리 전체 조회!!")
    @GetMapping("/findAll")
    public ResponseEntity<Map<String, Object>> write(@RequestHeader("TOKEN") String token) {
        logger.debug("# 토큰정보 {}: " + token);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // 2. 다이어리 목록 조회
            List<DiaryResponse> allDiary = diaryService.findAll(decodedToken.getUid());

            resultMap.put("response", allDiary);
            resultMap.put("error", 0);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
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
     * 다이어리 날짜 조회
     */
    @ApiOperation(value = "다이어리 날짜별 조회!!")
    @GetMapping("/findByDate/{date}")
    public ResponseEntity<Map<String, Object>> findByDate(@RequestHeader("TOKEN") String token,
                                                          @PathVariable String date) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // 2. 다이어리 날짜 조회
            List<DiaryResponse> byDate = diaryService.findByDate(decodedToken.getUid(), date);

            resultMap.put("response", byDate);
            resultMap.put("error", 0);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
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
     * 다이어리 ID 조회!!
     */
    @ApiOperation(value = "다이어리 ID 조회!!")
    @GetMapping("/find/{id}")
    public ResponseEntity<Map<String, Object>> write(@RequestHeader("TOKEN") String token,
                                                     @PathVariable Long id) {
        logger.debug("# 토큰정보 {}: " + token);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // 2. 다이어리 정보 조회
            DiaryResponse findDiary = diaryService.findById(id);
            resultMap.put("response", findDiary);
            resultMap.put("error", 0);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
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
     * 다이어리 작성! v333333333333333
     */
    @ApiOperation(value = "다이어리 작성 v3!!")
    @PostMapping("/write/v3")
    public ResponseEntity<Map<String, Object>> writeV3(@RequestHeader("TOKEN") String token,
                                                       DiaryRequestV3 request) {
        logger.debug("# 토큰정보 {}: " + token);
        Map<String, Object> resultMap = new HashMap<>();
        List<String> fileNames = new ArrayList<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

            if(request.getFiles()[0].getSize() != 0) {
                // 2. 이미지 업로드
                for (MultipartFile m : request.getFiles()) {
                    String fileName = s3Uploader.upload(m);
                    fileNames.add(fileName);
                }
            }

            // 2. 다이어리 작성
            boolean result = diaryService.writeDiaryV3(decodedToken.getUid(), request, fileNames);
            if (result) {
                resultMap.put("error", 0);
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            } else {
                resultMap.put("error", 1);
            }
        } catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
        } catch (IOException e2){
            resultMap.put("error", 1);
            resultMap.put("msg", "파일 업로드 실패!!!");
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }


    /**
     * 다이어리 수정! v2
     */
    @ApiOperation(value = "다이어리 수정!! v2")
    @PutMapping("/update/v2/{id}")
    public ResponseEntity<Map<String, Object>> updateV2(@RequestHeader("TOKEN") String token,
                                                      @PathVariable Long id,
                                                        DiaryRequestV2 request) {
        logger.debug("# 토큰정보 {}: " + token);
        Map<String, Object> resultMap = new HashMap<>();
        List<String> fileNames = new ArrayList<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

            if(request.getFiles()[0].getSize() != 0) {
                // 2. 이미지 업로드
                for (MultipartFile m : request.getFiles()) {
                    String fileName = s3Uploader.upload(m);
                    fileNames.add(fileName);
                }
            }

            // 2. 다이어리 수정
            boolean result = diaryService.updateV2(decodedToken.getUid(), id, request, fileNames);
            if (result) {
                // 2-1. 수정 성공!
                resultMap.put("error", 0);
                DiaryResponse findDiary = diaryService.findById(id);
                resultMap.put("response", findDiary);
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            } else {
                // 2-2. 수정 실패!
                resultMap.put("error", 1);
                resultMap.put("msg", "글 수정 실패!!");
            }
        } catch (FirebaseAuthException e) {
            resultMap.put("error", 1);
            AuthErrorCode authErrorCode = e.getAuthErrorCode();
            // 3. Token 만료 체크
            if (authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                resultMap.put("msg", "EXPIRED_ID_TOKEN");
            }
        }catch (IOException e2){
            resultMap.put("error", 1);
            resultMap.put("msg", "파일 업로드 실패!!!");
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }


    /**
     * 다이어리 내용 삭제!
     */
    @ApiOperation(value = "다이어리 삭제!!")
    @PutMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@RequestHeader("TOKEN") String token,
                                                      @PathVariable Long id) {
        logger.debug("# 토큰정보 {}: " + token);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // 2. 다이어리 삭제
            boolean result = diaryService.delete(decodedToken.getUid(), id);
            if (result) {
                // 2-1. 삭제 성공!
                resultMap.put("error", 0);
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            } else {
                // 2-2. 삭제 실패!
                resultMap.put("error", 1);
            }
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
}
