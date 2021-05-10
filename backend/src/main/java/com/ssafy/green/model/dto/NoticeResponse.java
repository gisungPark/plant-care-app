package com.ssafy.green.model.dto;

import com.ssafy.green.model.entity.plant.PlantInfo;
import lombok.Data;

@Data
public class NoticeResponse {
    String userId;
    String deviceToken;
    String nickname;

    public NoticeResponse(String userId, String deviceToken, String nickname) {
        this.userId = userId;
        this.deviceToken = deviceToken;
        this.nickname = nickname;
    }
}
