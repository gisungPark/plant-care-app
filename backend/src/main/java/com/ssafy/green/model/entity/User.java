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
@Table(name="User")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "u_id")
    private Long id;
    private String userId;
    private String password;
    private String nickname;
    private String profile;

    @Enumerated(EnumType.STRING)
    private UserType provider;
    private String providerId;

    @OneToMany(mappedBy = "user") // 연관관계 주인 rooms.user
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "user") // 연관관계 주인 diarys.user
    private List<Diary> diarys = new ArrayList<>();

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
