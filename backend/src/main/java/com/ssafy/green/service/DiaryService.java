package com.ssafy.green.service;

import com.ssafy.green.config.security.JwtTokenProvider;
import com.ssafy.green.model.dto.DiaryRequest;
import com.ssafy.green.model.dto.DiaryResponse;
import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.model.entity.DiaryImage;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.repository.DiaryImageRepository;
import com.ssafy.green.repository.DiaryRepository;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryImageRepository diaryImageRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 다이어리 작성
     */
    @Transactional
    public boolean writeDiary(String token, DiaryRequest diaryRequest){
        // 1. 회원 정보 찾기
        User findUser = getUserByToken(token);

        // 2. 다이어리 객체 생성
        Diary newDiary = Diary.builder()
                .user(findUser)
                .nickname(findUser.getNickname())
                .diaryTitle(diaryRequest.getDiaryTitle())
                .diaryContent(diaryRequest.getContent())
                .build();

        // 3. 다이어리 이미지 엔티티 연결
        List<DiaryImage> imgList = newDiary.getDiaryImages();
        for(String i : diaryRequest.getImgs()){
            // 4. 다이어리 이미지 저장!
            DiaryImage diaryImage = new DiaryImage(newDiary, i);
            newDiary.addImg(diaryImage);
        }

        // 5. 다이어리 저장!
        diaryRepository.save(newDiary);
        return true;
    }
    /**
     * 다이어리 전체 조회
     */
    public List<DiaryResponse> findAll(String token){

        // 1. 회원 정보 찾기
        User findUser = getUserByToken(token);

        List<Diary> allDiary = diaryRepository.findAllDiary(findUser.getId());
        List<DiaryResponse> diaryRes = new ArrayList<>();

        for(Diary d : allDiary){
            diaryRes.add(DiaryResponse.create(d));
        }
        return diaryRes;
    }

    /**
     * 다이어리 ID 조회
     * @return
     */
    public DiaryResponse findById(Long id){
        // 1. 회원 정보 찾기
        Diary findDiary = diaryRepository.findByIdAndFlag(id, true);
        return DiaryResponse.create(findDiary);
    }

    /**
     * 다이어리 수정!
     */
    @Transactional
    public boolean update(String token, Long id, DiaryRequest diaryRequest) {
        // 1. 회원 정보 찾기
        User findUser = getUserByToken(token);

        Optional<Diary> findDiary = diaryRepository.findById(id);

        if(findDiary.isPresent()){
            // 2. 다이어리 삭제 권한 체크 - 작성자가 본인이 맞다면,
            if(findUser == findDiary.get().getUser()) {
                Diary diary = findDiary.get();
                for(DiaryImage img : diary.getDiaryImages()){
                    diaryImageRepository.delete(img);
                }
                diary.update(diaryRequest);
                diaryRepository.save(diary);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * 해당 ID 다이어리 삭제!
     */
    @Transactional
    public boolean delete(String token, Long id) {
        // 1. 회원 정보 찾기
        User findUser = getUserByToken(token);

        // 2. id로 다이어리 조회
        Optional<Diary> findDiary = diaryRepository.findById(id);
        if(findDiary.isPresent()){
            // 2. 다이어리 삭제 권한 체크 - 작성자가 본인이 맞다면,
            if(findUser == findDiary.get().getUser()) {
                Diary diary = findDiary.get();
                
                // 3. 다이어리 이미지 목록 삭제
                diary.getDiaryImages().clear();
                // 4. flag값 flase로 전환
                diary.delete();
                System.out.println(diary.isFlag());
                diaryRepository.save(diary);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }

    
    /**
     * 식물별 다이어리 조회
     */


    /**
     * 토큰으로 유저정보 가져오기
     */
    public User getUserByToken(String token){
        // 0. 토큰 값에서 UserId 읽기
        String userId = jwtTokenProvider.getUserId(token);

        // 1. 회원 정보 찾기
        return userService.findUser(userId);
    }
    
}
