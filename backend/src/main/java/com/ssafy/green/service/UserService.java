package com.ssafy.green.service;

import com.ssafy.green.model.dto.RoomResponse;
import com.ssafy.green.model.dto.UserRequest;
import com.ssafy.green.model.dto.UserResponse;
import com.ssafy.green.model.dto.plant.MyPlantListResponse;
import com.ssafy.green.model.dto.plant.MyPlantResponse;
import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.model.entity.UserType;
import com.ssafy.green.repository.RoomRepository;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final String DEFALLT_IMG = "http://t1.daumcdn.net/liveboard/nylon/f14d6b83fcae464985e8c3090237cf2d.JPG";

    /**
     * 소셜 로그인
     */
    @Transactional
    public UserResponse oauthLogin(String userId) {
        UserResponse userResponse = new UserResponse();
        Optional<User> findUser = userRepository.findByUserIdAndFlag(userId, true);

        // 1. 최초 로그인 회원가입 처리
        if (!findUser.isPresent()) {
            System.out.println("#최초 로그인!!!");
            User newUser = User.builder()
                    .userId(userId)
                    .nickname("")
                    .provider(UserType.basic)
                    .providerId("")
                    .profile(DEFALLT_IMG)
                    .build();
            // 2. 회원 가입
            userRepository.save(newUser);
            findUser = userRepository.findByUserIdAndFlag(userId, true);
        }
        User user = findUser.get();
        // 3. callback 객체 생성
        System.out.println("#로그인 처리!!!");
        userResponse.setUserId(user.getUserId());
        userResponse.setNickname(user.getNickname());
        userResponse.setProfile(user.getProfile());
        userResponse.setThema(user.getThema());
        return userResponse;
    }

    /**
     * 내 식물 정보 호출
     */
    public List<MyPlantListResponse> findMyPlants(String userId){
        List<MyPlantListResponse> response = new ArrayList<>();

        // 1. 회원 정보 조회
        Optional<User> findUser = userRepository.findByUserIdAndFlag(userId, true);

        if(!findUser.isPresent()) return response;


        // 2. 방 정보 호출
        List<Room> RoomList = roomRepository.findByUserAndFlag(findUser.get(), true);
        for (Room room : RoomList) {
            response.addAll( RoomResponse.getPlantList(room));
        }
        
        return response;
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public UserResponse updateInfo(String userId, UserRequest userRequest) {
        // 1. userId로 회원 정보 조회
        UserResponse userResponse = new UserResponse();
        Optional<User> findUser = userRepository.findByUserIdAndFlag(userId, true);

        if(!findUser.isPresent()) return userResponse;
        User user = findUser.get();

        // 2. 회원 정보 수정
        user.updateInfo(userRequest);
        userRepository.save(user);

        // 3. callback 객체 생성
        userResponse.setUserId(user.getUserId());
        userResponse.setNickname(user.getNickname());
        userResponse.setProfile(user.getProfile());
        userResponse.setThema(user.getThema());
        return userResponse;
    }

    /**
     * 테마 변경
     */
    @Transactional
    public void changeThema(String userId, String thema){
        Optional<User> findUser = userRepository.findByUserIdAndFlag(userId, true);
        if(!findUser.isPresent()) return;
        User user = findUser.get();
        user.changeThema(thema);
    }

    /**
     * 회원 정보 삭제
     */
    @Transactional
    public boolean deleteUser(String userId){
        Optional<User> findUser = userRepository.findByUserIdAndFlag(userId, true);
        if(!findUser.isPresent()) return false;
        User user = findUser.get();
        user.delete();
        return true;
    }


    /**
     * 아이디로 회원 정보 조회
     */
    public User findUser(String userId){
        Optional<User> findUser = userRepository.findByUserIdAndFlag(userId, true);
        return findUser.get();
    }
}
