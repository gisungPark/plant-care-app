package com.ssafy.green.service;

import com.ssafy.green.config.security.JwtTokenProvider;
import com.ssafy.green.model.dto.RoomResponse;
import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.repository.RoomRepository;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public boolean createRoom(String token, String userId, String roomName) {

        // 1. 회원 정보 찾기
        User findUser = userService.findUser(userId);

        // 0. 유효한 방 리스트 호출
        List<Room> allRooms = roomRepository.findByUserAndFlag(findUser, true);
        // 1. 중복된 이름의 방 존재여부 체크!!!
        for(Room r: allRooms){
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
    public List<RoomResponse> findRooms(String token, String userId){
        // 1. 회원 정보 찾기
        User findUser = userService.findUser(userId);

        List<Room> RoomList = roomRepository.findByUserAndFlag(findUser, true);
        List<RoomResponse> list = new ArrayList<>();
        for(Room room : RoomList){
            list.add(new RoomResponse(room));
        }
        return list;
    }

    /**
     * 방 삭제
     */
    @Transactional
    public boolean deleteRoom(String token, Long id, String userId){

        // 1. 회원 정보 찾기
        User findUser = userService.findUser(userId);

        Optional<Room> result = roomRepository.findById(id);
        if(result.isPresent()){
            Room findRoom = result.get();
            if(findRoom.getUser() == findUser){
                findRoom.delete();
                roomRepository.save(findRoom);
                return true;
            }
        }
        return false;
    }

//    /**
//     * 토큰으로 유저정보 가져오기
//     */
//    public User getUserByToken(String token){
//        // 0. 토큰 값에서 UserId 읽기
//        String userId = jwtTokenProvider.getUserId(token);
//
//        // 1. 회원 정보 찾기
//        return userService.findUser(userId);
//    }
}
