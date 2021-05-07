package com.ssafy.green.model.dto;

import com.ssafy.green.model.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserResponse {
    private String userId;
    private String nickname;
    private String profile;
    private String theme;
    private String homeNickname;

    public UserResponse(User user){
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profile = user.getProfile();
        this.theme = user.getTheme();
        this.homeNickname =user.getHomeNickname();
    }
}
