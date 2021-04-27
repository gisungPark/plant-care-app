package com.ssafy.green.model.entity.plant;

import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.model.entity.DiaryImage;
import com.ssafy.green.model.entity.Room;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name="plant_care")
public class PlantCare {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "pid")
    @NonNull
    private PlantInfo plantInfo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @NonNull
    private Room room;

    private String nickname;
    private String name;
    private String water;
    private LocalDate started_date;
    private String image;
    private boolean dead;
    private boolean flag;

    @Builder
    public PlantCare(PlantInfo plantInfo, Room room, String nickname, LocalDate started_date, String name, String water) {
        this.plantInfo = plantInfo;
        this.room = room;
        this.nickname = nickname;
        this.name = name;
        this.started_date = started_date;
        this.water = water;
        this.dead = true;
        this.flag = true;
    }
}
