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
    private String content;
    private List<String> imgUrls = new ArrayList<>();
    private LocalDateTime writeDateTime;


    public static DiaryResponse create(Diary diary) {
        DiaryResponse diaryResponse = new DiaryResponse();
        diaryResponse.setId(diary.getId());
        diaryResponse.setPlantId(diary.getPlantCare().getId());
        diaryResponse.setContent(diary.getDiaryContent());

        for(DiaryImage img : diary.getDiaryImages()){
            diaryResponse.getImgUrls().add(img.getImgUrl());
        }

        diaryResponse.setWriteDateTime(diary.getWriteDateTime());

        return diaryResponse;
    }
}
