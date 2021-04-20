package com.ssafy.green.service;

import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.repository.RoomRepository;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    /**
     * 방 생성
     */
    public boolean createRoom(String userId, String roomName) {

        User findUser = userService.findUser(userId);
        // 0. 유효한 방 리스트 호출
        List<Room> rooms = findRooms(findUser.getUserId());
        // 0. 중복된 이름의 방 존재여부 체크!!!
        for(Room r: rooms){
            // 이름 중복!!
            if(r.getRoomName().equals(roomName)) return false;
        }

        Room newRoom = Room
                .builder()
                .user(findUser)
                .roomName(roomName)
                .build();

        // 1. 방 생성
        roomRepository.save(newRoom);

        // 2. 유저 맵핑
        rooms.add(newRoom);
        userRepository.save(findUser);

        return true;
    }

    /**
     * 방 전체 조회 (flag == true)
     */
    public List<Room> findRooms(String userId){
        User findUser = userService.findUser(userId);
        return roomRepository.findByUserAndFlag(findUser, true);
    }

    /**
     * 방 이름 조회
     */


    /**
     * 방 삭제
     */
    public boolean deleteRoom(String userId, String roomName){

        User findUser = userService.findUser(userId);
        Room findRoom = roomRepository.findByUserAndRoomNameAndFlag(findUser, roomName, true);

        findRoom.delete();
        roomRepository.save(findRoom);

        return true;
    }
}
