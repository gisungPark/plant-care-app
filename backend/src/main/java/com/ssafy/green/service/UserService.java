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
        User findUser = userRepository.findByUserId(user.getUserId());
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
        User findUser = userRepository.findByNickname(user.getNickname());
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
     * 로그인
     */
    public User login(User user){
        return userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword());
    }


    /**
     * 회원 정보 수정
     */

    /**
     * 회원 정보 삭제
     */

}
