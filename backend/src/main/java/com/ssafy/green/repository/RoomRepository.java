package com.ssafy.green.repository;

import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByUserAndFlag(User user, boolean flag);

    Room findByUserAndRoomNameAndFlag(User user, String roomName, boolean flag);
}
