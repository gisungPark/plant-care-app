package com.ssafy.green.repository;

import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserIdAndFlag(String userId, boolean flag);

    User findByNicknameAndFlag(String nickname, boolean flag);

    User findByUserIdAndPasswordAndFlag(String userId, String password, boolean flag);

}
