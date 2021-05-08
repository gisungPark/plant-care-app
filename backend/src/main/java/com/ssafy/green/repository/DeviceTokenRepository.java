package com.ssafy.green.repository;

import com.ssafy.green.model.entity.DeviceToken;
import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    List<DeviceToken> findAllByUser(User user);
    Optional<DeviceToken> findByUser(User user);
    Optional<DeviceToken> findByUserAndToken(User user, String token);
}
