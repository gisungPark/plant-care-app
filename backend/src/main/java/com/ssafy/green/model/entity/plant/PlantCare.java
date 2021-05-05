package com.ssafy.green.model.entity.plant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.green.model.entity.Room;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    private String startedDate;
    private String lastDate;
    private String image;
    private boolean dead;
    private boolean flag;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "plantCare")
    private List<Water> waterList;

    @Builder
    public PlantCare(PlantInfo plantInfo, Room room, String nickname, String startedDate, String name, String water, String image) {
        this.plantInfo = plantInfo;
        this.room = room;
        this.nickname = nickname;
        this.name = name;
        this.startedDate = startedDate;
        this.lastDate = startedDate;
        this.water = water;
        this.image = image;
        this.dead = true;
        this.flag = true;
    }


}
