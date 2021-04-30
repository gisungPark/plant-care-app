package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DiaryRequest {
    private Long plantId;
    private String title;
    private String content;
    private List<String> imgUrls;

}
