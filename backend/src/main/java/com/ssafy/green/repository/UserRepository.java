package com.ssafy.green.repository;

import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
    User findByNickname(String nickname);
    User findByUserIdAndPassword(String userId, String password);

}
