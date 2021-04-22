package com.ssafy.green.service;

import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.model.entity.User;
import com.ssafy.green.repository.DiaryRepository;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final UserService userService;
    private final DiaryRepository diaryRepository;

    /**
     * 다이어리 작성
     */
    public boolean writeDiary(String userId, Diary diary){
        User findUser = userService.findUser(userId);
        diary.setUser(findUser);
        diaryRepository.save(diary);
        return true;
    }


    /**
     * 다이어리 전체 조회
     */
    public List<Diary> findAll(String userId){
        User findUser = userService.findUser(userId);
        return diaryRepository.findByUserAndFlag(findUser, true);
    }

    /**
     * 식물별 다이어리 조회
     */


}
