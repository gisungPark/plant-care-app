package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public class DiaryRequestV2 {
    private Long plantId;
    private String title;
    private String content;
    private MultipartFile[] files;
}
