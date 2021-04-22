package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfoDto {

    private String userId;
    private String password;
    private String nickname;
    private String profile;
}
