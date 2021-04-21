package com.ssafy.green.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenResultDTO {
    private String token;
    private String userId;
    private Integer code;
}
