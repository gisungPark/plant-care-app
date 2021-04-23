package com.ssafy.green.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Table(name="Diary")
public class Diary {

    @Id @GeneratedValue
    @Column(name = "d_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private User user;

    @OneToMany(mappedBy = "diary")
    private List<DiaryImage> diaryImages = new ArrayList<>();

    //##################
    private String plantId = "C102";
    private String nickname;
    @Column(name = "d_title")
    private String diaryTitle;
    @Column(name = "d_content")
    private String diaryContent;
    @Column(name = "d_date")
    private LocalDateTime writeDateTime;
    @Column(name = "flag", columnDefinition = "boolean default true")
    private boolean flag = true;



    public Diary() {}

    @Builder
    public Diary(User user, String nickname, String diaryTitle, String diaryContent) {
        this.user = user;
        this.nickname = nickname;
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.writeDateTime = LocalDateTime.now();
    }

    public void addImg(DiaryImage img){
        this.diaryImages.add(img);
    }

}
