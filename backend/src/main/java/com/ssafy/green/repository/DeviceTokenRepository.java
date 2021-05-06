package com.ssafy.green.repository;

import com.ssafy.green.model.entity.DeviceToken;
import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    List<DeviceToken> findAllByUser(User user);
}
