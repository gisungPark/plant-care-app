package com.ssafy.green.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.green.model.entity.plant.PlantCare;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private String theme;

    @Column(name = "flag", columnDefinition = "boolean default true")
    private boolean flag = true;

    @Builder
    public Room(User user, String roomName, String theme) {
        this.user = user;
        this.roomName = roomName;
        this.theme = theme;
    }

    public void delete() {
        this.flag = false;
    }
}
