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
    public void createRoom(String userId, String roomName) {
        User findUser = userService.findUser(userId);
        Room newRoom = Room
                .builder()
                .user(findUser)
                .roomName(roomName)
                .build();

        // 1. 방 생성
        roomRepository.save(newRoom);

        // 2. 유저 맵핑
        List<Room> rooms = findUser.getRooms();
        rooms.add(newRoom);
        userRepository.save(findUser);
    }

    /**
     * 방 조회
     */

    /**
     * 방 삭제
     */

}
