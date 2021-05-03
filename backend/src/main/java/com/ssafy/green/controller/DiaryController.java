package com.ssafy.green.controller;


import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ssafy.green.model.dto.DiaryRequest;
import com.ssafy.green.model.dto.DiaryRequestV2;
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
    @ApiOperation(value = "다이어리 전체 조회!!", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n\n" +
            "Response\n" +
            "- id: 글 id\n" +
            "- plantId: 식물 id\n" +
            "- title: 제목\n" +
            "- content: 내용\n" +
            "- imgUrls: 이미지 리스트\n" +
            "- writeDateTime: 작성일\n" +
            "- error: 0[성공], 1[실패]")
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
    @ApiOperation(value = "다이어리 날짜별 조회!!", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "- date(PathVariable): 날짜(ex: 2021-04-28 ) \n\n" +
            "Response\n" +
            "- id: 글 id\n" +
            "- plantId: 식물 id\n" +
            "- title: 제목\n" +
            "- content: 내용\n" +
            "- imgUrls: 이미지 리스트\n" +
            "- writeDateTime: 작성일\n" +
            "- error: 0[성공], 1[실패]")
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
    @ApiOperation(value = "다이어리 ID 조회!!", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "- {id}(PathVariable): 다이어리 아이디\n\n" +
            "Response\n" +
            "- id:  다이어리 아이디\n" +
            "- plantId: 식물 아이디\n" +
            "- title: 제목\n" +
            "- content: 내용\n" +
            "- imgUrls: 이미지 url 목록\n" +
            "- writeDateTime: 작성 날짜\n" +
            "- error: 0[성공], 1[실패]")
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
     * 다이어리 작성!
     */
    @ApiOperation(value = "다이어리 작성!!", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "- plantId\n" +
            "- title\n" +
            "- content\n" +
            "- imgUrls (List)\n\n" +
            "Response\n" +
            "- error: 0[성공], 1[실패]")
    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> write(@RequestHeader("TOKEN") String token,
                                                     @RequestBody DiaryRequest request) {
        logger.debug("# 토큰정보 {}: " + token);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

            // 2. 다이어리 작성
            boolean result = diaryService.writeDiary(decodedToken.getUid(), request);
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
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }


    /**
     * 다이어리 작성! v2
     */
    @ApiOperation(value = "다이어리 작성 v2!!", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "- plantId\n" +
            "- title\n" +
            "- content\n" +
            "- files (List)\n\n" +
            "Response\n" +
            "- error: 0[성공], 1[실패]")
    @PostMapping("/write/v2")
    public ResponseEntity<Map<String, Object>> writeV2(@RequestHeader("TOKEN") String token,
                                                      DiaryRequestV2 request) {
        logger.debug("# 토큰정보 {}: " + token);
        Map<String, Object> resultMap = new HashMap<>();
        List<String> fileNames = new ArrayList<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

            // 2. 이미지 업로드
            for(MultipartFile m : request.getFiles()) {
                String fileName = s3Uploader.upload(m);
                fileNames.add(fileName);
            }

            // 2. 다이어리 작성
            boolean result = diaryService.writeDiaryV2(decodedToken.getUid(), request, fileNames);
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
     * 다이어리 수정!
     */
    @ApiOperation(value = "다이어리 수정!!", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "- {id}(PathVariable): 다이어리 아이디\n" +
            "- content: 내용\n" +
            "- plantId: 식물 아이디\n" +
            "- imgUrls (List)\n\n" +
            "Response\n" +
            "- id:  다이어리 아이디\n" +
            "- plantId: 식물 아이디\n" +
            "- content: 다이어리 내용\n" +
            "- imgUrls: 이미지 url 목록\n" +
            "- writeDateTime: 작성 날짜\n" +
            "- error: 0[성공], 1[실패]")
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@RequestHeader("TOKEN") String token,
                                                      @PathVariable Long id,
                                                      @RequestBody DiaryRequest diaryRequest) {
        logger.debug("# 토큰정보 {}: " + token);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // 2. 다이어리 작성
            boolean result = diaryService.update(decodedToken.getUid(), id, diaryRequest);
            if (result) {
                // 2-1. 수정 성공!
                resultMap.put("error", 0);
                DiaryResponse findDiary = diaryService.findById(id);
                resultMap.put("response", findDiary);
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            } else {
                // 2-2. 수정 실패!
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

    /**
     * 다이어리 내용 삭제!
     */
    @ApiOperation(value = "다이어리 삭제!!", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "- {id}(PathVariable): 다이어리 아이디\n\n" +
            "Response\n" +
            "- error: 0[성공], 1[실패]")
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
