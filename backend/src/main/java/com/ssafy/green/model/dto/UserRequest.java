package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequest {
    private String nickname;
    private String profile;
}
