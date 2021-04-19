package com.ssafy.green.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uid")
    private Long id;

    private String userId;
    private String password;
    private String nickname;
    private String profile;
    private String provider;
    private String providerId;
    @Column(columnDefinition = "boolean default true")
    private Boolean flag = true;

    public User() {}

    @Builder
    public User(String userId, String password, String nickname, String profile, String provider, String providerId) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void updateInfo(User user) {
        this.password = user.password;
        this.nickname = user.nickname;
        this.profile = user.profile;
    }

    public void delete() {
        this.flag = false
    }
}
