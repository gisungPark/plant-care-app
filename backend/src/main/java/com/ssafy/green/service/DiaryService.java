package com.ssafy.green.service;

import com.ssafy.green.config.security.JwtTokenProvider;
import com.ssafy.green.model.dto.DiaryDto;
import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.model.entity.DiaryImage;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.repository.DiaryImageRepository;
import com.ssafy.green.repository.DiaryRepository;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final UserService userService;
    private final DiaryRepository diaryRepository;
    private final DiaryImageRepository diaryImageRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 다이어리 작성
     */
    @Transactional
    public boolean writeDiary(String token, DiaryDto diaryDto){
        // 0. 토큰 값에서 UserId 읽기
        String userId = jwtTokenProvider.getUserId(token);

        // 1. 회원 정보 찾기
        User findUser = userService.findUser(userId);
        
        // 2. 다이어리 객체 생성
        Diary newDiary = Diary.builder()
                .user(findUser)
                .nickname(findUser.getNickname())
                .diaryTitle(diaryDto.getDiaryTitle())
                .diaryContent(diaryDto.getContent())
                .build();

        // 3. 다이어리 이미지 엔티티 연결
        List<DiaryImage> imgList = newDiary.getDiaryImages();

        // 4. 다이어리 저장!
        diaryRepository.save(newDiary);
        for(String i : diaryDto.getImgs()){
            // 5. 다이어리 이미지 저장!
            DiaryImage diaryImage = new DiaryImage(newDiary, i);
            diaryImageRepository.save(diaryImage);
        }
        return true;
    }


    /**
     * 다이어리 전체 조회
     */
    public List<Diary> findAll(String token){
        // 0. 토큰 값에서 UserId 읽기
        String userId = jwtTokenProvider.getUserId(token);

        // 1. 회원 정보 찾기
        User findUser = userService.findUser(userId);
        return diaryRepository.findByUserAndFlag(findUser, true);
    }

    /**
     * 식물별 다이어리 조회
     */


}
