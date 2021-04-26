package com.ssafy.green.model.entity;

import com.ssafy.green.model.entity.plant.PlantCare;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @OneToMany(mappedBy = "room")
    private List<PlantCare> plantCares;

    private String roomName;
    @Column(name = "flag", columnDefinition = "boolean default true")
    private boolean flag = true;

    @Builder
    public Room(User user, String roomName) {
        this.user = user;
        this.roomName = roomName;
    }

    public void addPlant(PlantCare plantCare){
        this.plantCares.add(plantCare);
        plantCare.setRoom(this);
    }
    public void delete() {
        this.flag = false;
    }
}
