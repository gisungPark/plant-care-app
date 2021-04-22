package com.ssafy.green.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="diaryImage")
@Getter
@ToString
public class DiaryImage {

    @Id @GeneratedValue
    @Column(name = "di_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_id")
    private Diary diary;

    @Column(name = "di_url")
    private String imgUrl;

    public DiaryImage() {}

    @Builder
    public DiaryImage(Diary diary, String imgUrl) {
        this.diary = diary;
        this.imgUrl = imgUrl;
    }

    public void setDiary(Diary diary){
        this.diary = diary;
    }
}
