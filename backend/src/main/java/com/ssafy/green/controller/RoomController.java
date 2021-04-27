package com.ssafy.green.controller;

import com.ssafy.green.model.dto.RoomResponse;
import com.ssafy.green.service.RoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            "- success: 방 생성 성공\n" +
            "- fail: 방 생성 실패!\n" +
            "- nickname: 닉네임\n")
    @PostMapping("/create")
    public String create(@RequestHeader("TOKEN") String token, @RequestBody String roomName){
        boolean room = roomService.createRoom(token, roomName);
        if(room){
            return "success";
        }
        return "fail";
    }

    /**
     * 방 조회
     */
    @GetMapping("/find")
    public List<RoomResponse> find(@RequestHeader("TOKEN") String token){
        return roomService.findRooms(token);
    }

    /**
     * 방 삭제
     */
    @PutMapping("/delete/{id}")
    public String delete(@RequestHeader("TOKEN") String token, @PathVariable Long id){
        boolean result = roomService.deleteRoom(token, id);
        if(result){
            return "success";
        }
        return "fail";
    }
}
