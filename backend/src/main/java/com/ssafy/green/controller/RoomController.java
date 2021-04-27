package com.ssafy.green.controller;

import com.ssafy.green.model.dto.RoomResponse;
import com.ssafy.green.service.RoomService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "방 생성", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- roomName: 생성할 방 이름\n\n" +
            "Response\n" +
            "- true: 방 생성 성공\n" +
            "- false: 방 생성 실패!\n")
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestHeader("TOKEN") String token, @RequestBody String roomName){
        Map<String,Object> resultMap = new HashMap<>();
        boolean result = roomService.createRoom(token, roomName);
        resultMap.put("response", result);
        if(result){
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }

    /**
     * 방 조회
     */
    @ApiOperation(value = "모든 방 조회(그 안 식물까지)", notes = "사용자 로그인 토큰 필요")
    @GetMapping("/find")
    public ResponseEntity<Map<String, Object>> find(@RequestHeader("TOKEN") String token){

        Map<String,Object> resultMap = new HashMap<>();
        List<RoomResponse> allRooms = roomService.findRooms(token);
        resultMap.put("response", allRooms);

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    /**
     * 방 삭제
     */
    @ApiOperation(value = "방 삭제", notes="Parameter\n" +
            "- token(RequestHeader): 액세스 토큰\n" +
            "- {id}(PathVariable) : 삭제할 방 이름\n\n" +
            "Response\n" +
            "- true: 방 삭제 성공\n" +
            "- false: 방 삭제 실패!\n")
    @PutMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@RequestHeader("TOKEN") String token, @PathVariable Long id){
        Map<String,Object> resultMap = new HashMap<>();
        boolean result = roomService.deleteRoom(token, id);
        resultMap.put("response", result);
        if(result){
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
    }
}
