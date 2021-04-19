package com.ssafy.green.repository;

import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserIdAndFlag(String userId, boolean uFlag);

    User findByNicknameAndFlag(String nickname, boolean uFlag);

    User findByUserIdAndPasswordAndFlag(String userId, String password, boolean uFlag);
}
