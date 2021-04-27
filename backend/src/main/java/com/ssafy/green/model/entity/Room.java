package com.ssafy.green.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.green.model.entity.plant.PlantCare;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="room")
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "room")
    private List<PlantCare> plantList;

    private String roomName;

    @Column(name = "flag", columnDefinition = "boolean default true")
    private boolean flag = true;

    @Builder
    public Room(User user, String roomName) {
        this.user = user;
        this.roomName = roomName;
    }

    public void delete() {
        this.flag = false;
    }
}
