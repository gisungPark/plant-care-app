package com.ssafy.green.model.entity.plant;

import lombok.*;
import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@Table(name="plant_care")
public class PlantCare {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pid;
    private Long rid;
    private String nickname;
    private String water;
    private String info;
    private String image;
    private boolean dead;
    private boolean flag;

    @Builder
    public PlantCare(Long pid, Long rid, String nickname, String water, String info, boolean dead, boolean flag) {
        this.pid = pid;
        this.rid = rid;
        this.nickname = nickname;
        this.water = water;
        this.info = info;
        this.dead = true;
        this.flag = true;
    }
}
