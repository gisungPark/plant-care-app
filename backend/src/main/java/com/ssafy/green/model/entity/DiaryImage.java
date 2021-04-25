package com.ssafy.green.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="dimage")
@Getter
@NoArgsConstructor
public class DiaryImage {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did")
    private Diary diary;

    @Column(name = "image")
    private String imgUrl;


    @Builder
    public DiaryImage(Diary diary, String imgUrl) {
        this.diary = diary;
        this.imgUrl = imgUrl;
    }

}
