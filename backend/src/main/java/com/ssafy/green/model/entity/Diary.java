package com.ssafy.green.model.entity;

import com.ssafy.green.model.dto.DiaryRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="Diary")
@NoArgsConstructor
public class Diary {

    @Id @GeneratedValue
    @Column(name = "d_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private User user;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryImage> diaryImages = new ArrayList<>();

    //##################
    @Column(name = "pc_id")
    private Long plantId = 1L;
    private String nickname;
    @Column(name = "d_title")
    private String diaryTitle;
    @Column(name = "d_content")
    private String diaryContent;
    @Column(name = "d_date")
    private LocalDateTime writeDateTime;
    @Column(name = "flag", columnDefinition = "boolean default true")
    private boolean flag = true;

    @Builder
    public Diary(User user, String nickname, String diaryTitle, String diaryContent) {
        this.user = user;
        this.nickname = nickname;
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.writeDateTime = LocalDateTime.now();
    }

    // ###########################################
    /**
     * 이미지 정보 추가
     */
    public void addImg(DiaryImage img){
        this.diaryImages.add(img);
    }

    /**
     * 다이어리 정보 수정
     */
    public void update(DiaryRequest diaryRequest) {
        if(this.diaryImages != null){
            this.diaryImages.clear();
        }
        this.diaryTitle = diaryRequest.getDiaryTitle();
        this.diaryContent = diaryRequest.getContent();
        this.writeDateTime = LocalDateTime.now();

        for(String img: diaryRequest.getImgs()){
            DiaryImage diaryImage = new DiaryImage(this, img);
            this.addImg(diaryImage);
        }
    }

    /**
     * 다이어리 삭제!
     */
    public void delete() {
        this.flag = false;
    }
}
