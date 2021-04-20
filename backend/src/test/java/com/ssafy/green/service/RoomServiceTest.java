package com.ssafy.green.service;


import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoomServiceTest {

    @Autowired UserService userService;
    @Autowired RoomService roomService;

    @Test
    public void 방생성_테스트() throws Exception{
        //given
        User newUser = User.builder()
                .userId("ssafy")
                .nickname("고구마")
                .password("1234")
                .provider("google")
                .providerId("google")
                .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                .build();
        //when
        //1. 방생성
        boolean result = userService.join(newUser);
        roomService.createRoom(newUser.getUserId(), "거실");
        roomService.createRoom(newUser.getUserId(), "작은방");
        roomService.createRoom(newUser.getUserId(), "큰방");
        roomService.createRoom(newUser.getUserId(), "화장실");

        // 2. 방 리스트
        User findUser = userService.findUser(newUser.getUserId());

        //then
        Assert.assertEquals(4, findUser.getRooms().size());
     }

     @Test
     @Rollback(false)
     public void 방삭제_테스트() throws Exception{
         //given
         //given
         User newUser = User.builder()
                 .userId("ssafy")
                 .nickname("고구마")
                 .password("1234")
                 .provider("google")
                 .providerId("google")
                 .profile("http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG")
                 .build();
         //when
         //1. 방생성
         boolean result = userService.join(newUser);
         roomService.createRoom(newUser.getUserId(), "거실");
         roomService.createRoom(newUser.getUserId(), "작은방");
         roomService.createRoom(newUser.getUserId(), "큰방");
         roomService.createRoom(newUser.getUserId(), "화장실");

         // 2. 큰방 삭제
         roomService.deleteRoom(newUser.getUserId(), "큰방");

         List<Room> rooms = roomService.findRooms(newUser.getUserId());
         for (Room r: rooms){
             System.out.println(r.toString());
         }

         //then
         Assert.assertEquals(3, rooms.size());
      }


}