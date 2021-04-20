package com.ssafy.green.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Diary {

    @Id @GeneratedValue
    @Column(name = "did")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    @Setter
    private User user;
    //##################
    private String plantId = "잡초";
    private String nickname;
    private String diaryName;
    private String diaryContent;
    private LocalDateTime writeDateTime;
    @Column(name = "dflag", columnDefinition = "boolean default true")
    private boolean flag = true;

    public Diary() {}

    @Builder
    public Diary(User user, String nickname, String diaryName, String diaryContent) {
        this.user = user;
        this.nickname = nickname;
        this.diaryName = diaryName;
        this.diaryContent = diaryContent;
        this.writeDateTime = LocalDateTime.now();
    }


}
