package com.ssafy.green.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @ToString
public class Room {

    @Id @GeneratedValue
    @Column(name = "rid")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;
    private String roomName;

    public Room() {}

    @Builder
    public Room(User user, String roomName) {
        this.user = user;
        this.roomName = roomName;
    }
}
