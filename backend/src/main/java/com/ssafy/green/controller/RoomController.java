package com.ssafy.green.controller;

import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ssafy.green.model.dto.RoomResponse;
import com.ssafy.green.service.RoomService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    /**
     * 방 생성
     */
    @ApiOperation(value = "방 생성", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "- roomName: 생성할 방 이름\n\n" +
            "Response\n" +
            "- true: 방 생성 성공\n" +
            "- false: 방 생성 실패!\n")
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestHeader("TOKEN") String token,
                                                      @RequestBody CreateRoomRequest request) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // 2. 방 생성!
            boolean result = roomService.createRoom(decodedToken.getUid(), request.getRoomName());
            if (result) {
                resultMap.put("error", 0);
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            } else {
                resultMap.put("error", 1);
                resultMap.put("msg", "이미 존재하는 방 입니다.");
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

    @Data
    static class CreateRoomRequest {
        private String roomName;
    }

    /**
     * 방 조회
     */
    @ApiOperation(value = "모든 방 조회(그 안 식물까지)", notes = "사용자 로그인 토큰 필요")
    @GetMapping("/find")
    public ResponseEntity<Map<String, Object>> find(@RequestHeader("TOKEN") String token) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // 2. 방 생성!
            List<RoomResponse> allRooms = roomService.findRooms(decodedToken.getUid());
            resultMap.put("response", allRooms);
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

    @Data
    static class FineRoomRequest {
        private String userId;
    }

    /**
     * 방 삭제
     */
    @ApiOperation(value = "방 삭제", notes = "Parameter\n" +
            "- token(RequestHeader) : Firebase token\n" +
            "- {id}(PathVariable) : 삭제할 방 번호\n\n" +
            "Response\n" +
            "- true: 방 삭제 성공\n" +
            "- false: 방 삭제 실패!\n")
    @PutMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@RequestHeader("TOKEN") String token,
                                                      @PathVariable Long id) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 1. Firebase Token decoding
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // 2. 방 삭제!
            boolean result = roomService.deleteRoom(decodedToken.getUid(), id);
            if (result) {
                resultMap.put("error", 0);
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            } else {
                // 2-1. 내 방이 아닌경우,
                // 2-2. 해당 이름의 방이 존재하지 않는 경우,
                resultMap.put("error", 1);
                resultMap.put("msg", "방을 확인해 주세요.");
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
