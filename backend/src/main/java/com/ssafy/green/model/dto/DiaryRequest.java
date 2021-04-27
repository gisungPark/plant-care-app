package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DiaryRequest {
    private String plantId;
    private String content;
    private List<String> imgs;

}
