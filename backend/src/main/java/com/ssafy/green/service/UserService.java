package com.ssafy.green.service;

import com.ssafy.green.model.entity.User;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 아이디 중복체크
     */
    public boolean validateDuplicateUserId(User user){
        User findUser = userRepository.findByUserIdAndFlag(user.getUserId(),true);
        if(findUser == null){
            return true; // 사용가능한 아이디 입니다.!
        }else{
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    /**
     * 닉네임 중복체크
     */
    public boolean validateDuplicateNickname(User user){
        User findUser = userRepository.findByNicknameAndFlag(user.getNickname(), true);
        if(findUser == null){
            return true; // 사용가능한 닉네임 입니다.!
        }else{
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    /**
     * 회원 가입!
     */
    public boolean join(User user) throws Exception{
        if(validateDuplicateUserId(user) && validateDuplicateNickname(user)){
            userRepository.save(user);
        }
        return true;
    }

    /**
     * 아이디로 회원 정보 조회
     */
    public User findUser(String userId){
        return userRepository.findByUserIdAndFlag(userId, true);
    }

    /**
     * 로그인
     */
    public User login(User user){
        return userRepository.findByUserIdAndPasswordAndFlag(user.getUserId(), user.getPassword(), true);
    }

    /**
     * 회원 정보 수정
     */
    public User updateInfo(User user){
        User findUser = userRepository.findByUserIdAndFlag(user.getUserId(), true);
        findUser.updateInfo(user);
        userRepository.save(findUser);
        return findUser;
    }

    /**
     * 회원 정보 삭제
     */
    public boolean deleteUser(User user){
        User findUser = userRepository.findByUserIdAndFlag(user.getUserId(), true);
        findUser.delete();
        userRepository.save(findUser);
        return true;
    }
}
