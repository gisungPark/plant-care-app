package com.ssafy.green.model.dto;

import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.model.entity.DiaryImage;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class DiaryResponse {
    private Long id;
    private Long plantId;
    private String diaryTitle;
    private String diaryContent;
    private List<String> imgs = new ArrayList<>();
    private String nickname;
    private LocalDateTime writeDateTime;


    public static DiaryResponse create(Diary diary) {
        DiaryResponse diaryResponse = new DiaryResponse();
        diaryResponse.setId(diary.getId());
        diaryResponse.setPlantId(diary.getPlantId());
        diaryResponse.setDiaryTitle(diary.getDiaryTitle());
        diaryResponse.setDiaryContent(diary.getDiaryContent());

        for(DiaryImage img : diary.getDiaryImages()){
            diaryResponse.getImgs().add(img.getImgUrl());
        }

        diaryResponse.setNickname(diary.getNickname());
        diaryResponse.setWriteDateTime(diary.getWriteDateTime());

        return diaryResponse;
    }
}
