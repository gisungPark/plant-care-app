package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DiaryRequest {
    private String diaryTitle;
    private String content;
    private String plantId;
    private List<String> imgs;

}
