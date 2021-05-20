package com.ssafy.green.model.entity;

import com.ssafy.green.model.entity.plant.PlantInfo;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mbti_result")
public class MbtiResult {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String summary;
    private String etc;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "pid1")
    @NonNull
    private PlantInfo plantInfo1;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "pid2")
    @NonNull
    private PlantInfo plantInfo2;

    @Builder
    public MbtiResult(String type, String summary, String etc, PlantInfo plantInfo1, PlantInfo plantInfo2){
        this.type = type;
        this.summary = summary;
        this.etc = etc;
        this.plantInfo1 = plantInfo1;
        this.plantInfo2 = plantInfo2;
    }
}
