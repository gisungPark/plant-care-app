package com.ssafy.green.service;

import com.ssafy.green.config.security.JwtTokenProvider;
import com.ssafy.green.model.dto.OauthResponse;
import com.ssafy.green.model.dto.UserResponse;
import com.ssafy.green.model.dto.UserRequest;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.model.entity.UserType;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final String DEFALLT_IMG = "http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG";
    /**
     * 아이디 중복체크
     */
    public boolean validateDuplicateUserId(String userId){
        User findUser = userRepository.findByUserIdAndFlag(userId,true);
        if(findUser == null){
            return true; // 사용가능한 아이디 입니다.!
        }else{
            return  false;
        }
    }

    /**
     * 닉네임 중복체크
     */
    public boolean validateDuplicateNickname(String nickname){
        User findUser = userRepository.findByNicknameAndFlag(nickname, true);
        if(findUser == null){
            return true; // 사용가능한 닉네임 입니다.!
        }else{
            return  false;
        }
    }

    /**
     * 회원 가입!
     */
    @Transactional
    public boolean join(UserRequest userRequest) throws Exception{

        if(validateDuplicateUserId(userRequest.getUserId()) 
                    && validateDuplicateNickname(userRequest.getNickname())){
            
            User newUser = User.builder()
                    .userId(userRequest.getUserId())
                    .password(userRequest.getPassword())
                    .nickname(userRequest.getNickname())
                    .provider(UserType.basic)
                    .providerId("")
                    .profile(DEFALLT_IMG)
                    .build();
            // 회원 가입
            userRepository.save(newUser);
        }else {
            System.out.println("이미 존재하는 회원입니다.");
            return false;
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
    public UserResponse login(UserRequest userRequest){
        UserResponse userResponse = new UserResponse();
        User findUser = userRepository.findByUserIdAndPasswordAndFlag(userRequest.getUserId(), userRequest.getPassword(), true);

        // 1. 회원 존재여부 확인
        if(findUser != null){
            // 2. token 생성 및 기본정보 세팅
            userResponse.setToken(jwtTokenProvider.generateToken(findUser.getUserId()));
            userResponse.setUserId(findUser.getUserId());
            userResponse.setNickname(findUser.getNickname());
            userResponse.setProfile(findUser.getProfile());
            userResponse.setCode(0);
        } else {
            userResponse.setCode(1);
        }

        return userResponse;
    }

    /**
     * 소셜 로그인
     */
    @Transactional
    public UserResponse oauthLogin(OauthResponse oauth) {
        UserResponse userResponse = new UserResponse();
        User findUser = userRepository.findByUserIdAndFlag(oauth.getId(), true);

        if(findUser == null){
            if(validateDuplicateUserId(oauth.getId())
                    && validateDuplicateNickname(oauth.getNickname())){

                User newUser = User.builder()
                        .userId(oauth.getId())
                        .password(oauth.getId())
                        .nickname(oauth.getNickname())
                        .provider(UserType.basic)
                        .providerId("")
                        .profile(DEFALLT_IMG)
                        .build();
                // 회원 가입
                userRepository.save(newUser);
            }else {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            }
        }else {
            userResponse.setToken(jwtTokenProvider.generateToken(findUser.getUserId()));
            userResponse.setUserId(findUser.getUserId());
            userResponse.setNickname(findUser.getNickname());
            userResponse.setProfile(findUser.getProfile());
            userResponse.setCode(0);
        }
        return userResponse;
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public UserResponse updateInfo(String token, UserRequest userRequest){
        // 0. 토큰 값에서 UserId 읽기
        String userId = jwtTokenProvider.getUserId(token);
        UserResponse userResponse = new UserResponse();

        // 1. userId로 회원 정보 검색
        System.out.println(userId + " 값으로 회원정보 검색");
        User findUser = userRepository.findByUserIdAndFlag(userId, true);

        // 2. 회원 정보 수정
        findUser.updateInfo(userRequest);
        userRepository.save(findUser);

        // 3. callback 객체 생성
        userResponse.setToken(jwtTokenProvider.generateToken(findUser.getUserId()));
        userResponse.setUserId(findUser.getUserId());
        userResponse.setNickname(findUser.getNickname());
        userResponse.setProfile(findUser.getProfile());
        userResponse.setCode(0);

        return userResponse;
    }

    /**
     * 회원 정보 삭제
     */
    @Transactional
    public boolean deleteUser(User user){
        User findUser = userRepository.findByUserIdAndFlag(user.getUserId(), true);
        findUser.delete();
        userRepository.save(findUser);
        return true;
    }


}
