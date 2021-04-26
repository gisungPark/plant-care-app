package com.ssafy.green.model.entity;

import com.ssafy.green.model.dto.UserRequest;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name="user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long id;
    private String userId;
    private String password;
    private String nickname;
    private String profile;

    @Enumerated(EnumType.STRING)
    private UserType provider;
    private String providerId;
    private String thema;

    @Column(name = "flag", columnDefinition = "boolean default true")
    private Boolean flag = true;

    @Builder
    public User(String userId, String password, String nickname, String profile, UserType provider, String providerId) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.provider = provider;
        this.providerId = providerId;
        this.thema = "DEAFULT_THEMA_IMAGE";
    }

    /**
     * 유저 정보 수정
     */
    public void updateInfo(UserRequest userInfo) {
        this.password = userInfo.getPassword();
        this.nickname = userInfo.getNickname();
        this.profile = userInfo.getProfile();
    }

    /**
     * 테마 정보 수정
     */
    public void changeThema(String thema){
        this.thema = thema;
    }

    /**
     * 유저 정보 삭제
     */
    public void delete() {
        this.flag = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority("USER"));
        return auth;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
