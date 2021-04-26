package com.ssafy.green.service;

import com.ssafy.green.config.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 방 생성
     */
    @Transactional
    public boolean createRoom(String token, String roomName) {

        User findUser = getUserByToken(token);

        // 0. 유효한 방 리스트 호출
        List<Room> rooms = findRooms(findUser.getUserId());
        // 1. 중복된 이름의 방 존재여부 체크!!!
        for(Room r: rooms){
            // 이름 중복!!
            if(r.getRoomName().equals(roomName)) return false;
        }

        Room newRoom = Room.builder()
                .user(findUser)
                .roomName(roomName)
                .build();

        roomRepository.save(newRoom);
        return true;
    }

    /**
     * 방 전체 조회 (flag == true)
     */
    public List<Room> findRooms(String token){
        User findUser = getUserByToken(token);
        return roomRepository.findByUserAndFlag(findUser, true);
    }

    /**
     * 방 이름 조회
     */


    /**
     * 방 삭제
     */
    @Transactional
    public boolean deleteRoom(String userId, String roomName){

        User findUser = userService.findUser(userId);
        Room findRoom = roomRepository.findByUserAndRoomNameAndFlag(findUser, roomName, true);

        findRoom.delete();
        roomRepository.save(findRoom);

        return true;
    }

    /**
     * 토큰으로 유저정보 가져오기
     */
    public User getUserByToken(String token){
        // 0. 토큰 값에서 UserId 읽기
        String userId = jwtTokenProvider.getUserId(token);

        // 1. 회원 정보 찾기
        return userService.findUser(userId);
    }
}
