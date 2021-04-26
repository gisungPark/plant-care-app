package com.ssafy.green.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name="diary_image")
@NoArgsConstructor
public class DiaryImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_image_id")
    private Long id;
    private String imgUrl;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;


    @Builder
    public DiaryImage(Diary diary, String imgUrl) {
        this.diary = diary;
        this.imgUrl = imgUrl;
    }

}
