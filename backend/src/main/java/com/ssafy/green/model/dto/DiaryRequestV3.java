package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@ToString
public class DiaryRequestV3 {
    private Long plantId;
    private String title;
    private String content;
    private String writeDateTime;
    private MultipartFile[] files;
}
