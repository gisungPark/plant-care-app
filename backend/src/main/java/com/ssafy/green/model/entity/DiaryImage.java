package com.ssafy.green.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="DiaryImage")
@Getter
@NoArgsConstructor
public class DiaryImage {

    @Id @GeneratedValue
    @Column(name = "di_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_id")
    private Diary diary;

    @Column(name = "di_url")
    private String imgUrl;


    @Builder
    public DiaryImage(Diary diary, String imgUrl) {
        this.diary = diary;
        this.imgUrl = imgUrl;
    }

}
