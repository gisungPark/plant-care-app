package com.ssafy.green.repository;

import com.ssafy.green.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {

}
