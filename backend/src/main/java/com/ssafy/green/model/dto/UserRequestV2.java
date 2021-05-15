package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public class UserRequestV2 {
    private String nickname;
    private MultipartFile profile;
}
