package com.ssafy.green.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    /**
     * 방 생성
     */
    @PostMapping("/create")
    public void create(){

    }

    /**
     * 방 조회
     */
    @GetMapping("/find/{id}")
    public void find(){

    }

    /**
     * 방 삭제
     */
    @PutMapping("/delete/{id}")
    public void delete(){

    }
}
