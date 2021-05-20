package com.ssafy.green.model.entity;

import com.ssafy.green.model.dto.DiaryRequest;
import com.ssafy.green.model.dto.DiaryRequestV2;
import com.ssafy.green.model.entity.plant.PlantCare;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private PlantCare plantCare;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryImage> diaryImages = new ArrayList<>();

    @Column(name = "title")
    private String diaryTitle;


    @Column(name = "content")
    private String diaryContent;
    @Column(name = "created_date")
    private LocalDateTime writeDateTime;
    @Column(name = "flag", columnDefinition = "boolean default true")
    private boolean flag = true;

    @Builder
    public Diary(User user, PlantCare plantCare, String title, String content) {
        this.user = user;
        this.plantCare = plantCare;
        this.diaryTitle = title;
        this.diaryContent = content;
        this.writeDateTime = LocalDateTime.now();
    }


    public void setWriteDateTime(String date){
        String[] splits = date.split("-");
        int year = Integer.parseInt(splits[0]);
        int month = Integer.parseInt(splits[1]);
        int day = Integer.parseInt(splits[2]);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime writeDateTime = LocalDateTime.of(LocalDate.of(year, month, day),
                            LocalTime.of(now.getHour(), now.getMinute(), now.getSecond()));
        this.writeDateTime = writeDateTime;
    }

    /**
     * 이미지 정보 추가
     */
    public void addImg(DiaryImage img){
        this.diaryImages.add(img);
        img.setDiary(this);
    }

    /**
     * 다이어리 정보 수정
     */
    public void update(DiaryRequest diaryRequest) {
        if(this.diaryImages != null){
            this.diaryImages.clear();
        }
        this.diaryTitle = diaryRequest.getTitle();
        this.diaryContent = diaryRequest.getContent();

        for(String img: diaryRequest.getImgUrls()){
            DiaryImage diaryImage = new DiaryImage(this, img);
            this.addImg(diaryImage);
        }
    }

    /**
     * 다이어리 수정 v2
     */
    public void updateV2(DiaryRequestV2 request, List<String> fileNames) {
        if(this.diaryImages != null){
            this.diaryImages.clear();
        }
        this.diaryTitle = request.getTitle();
        this.diaryContent = request.getContent();

        for(String img: fileNames){
            DiaryImage diaryImage = new DiaryImage(this, img);
            this.addImg(diaryImage);
        }

    }

    /**
     * 다이어리 삭제!
     */
    public void delete() {
        this.flag = false;
        this.diaryImages.clear();
    }


}
