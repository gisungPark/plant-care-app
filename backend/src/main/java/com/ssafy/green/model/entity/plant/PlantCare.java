package com.ssafy.green.model.entity.plant;

import com.ssafy.green.model.entity.Room;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
    private String nickname;
    private String water;
    private String info;
    private String image;
    private boolean dead;
    private boolean flag;

    @Builder
    public PlantCare(Long pid, Room room, String nickname, String water, String info, boolean dead, boolean flag) {
        this.pid = pid;
        this.room = room;
        this.nickname = nickname;
        this.water = water;
        this.info = info;
        this.dead = true;
        this.flag = true;
    }
}
