package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JoinDto {

    private String userId;
    private String password;
    private String nickname;
}
