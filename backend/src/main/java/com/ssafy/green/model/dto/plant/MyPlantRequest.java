package com.ssafy.green.model.dto.plant;

import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


@Data
@ToString
@NoArgsConstructor
public class MyPlantRequest {
    private Long pid;
    private Long rid;
    private String nickname;
    private String startedDate;
    private MultipartFile image;
}
