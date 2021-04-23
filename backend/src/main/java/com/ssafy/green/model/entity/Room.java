package com.ssafy.green.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @ToString
@Table(name="Room")
public class Room {

    @Id @GeneratedValue
    @Column(name = "r_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private User user;
    @JoinColumn(name = "r_name")
    private String roomName;
    @Column(name = "flag", columnDefinition = "boolean default true")
    private boolean flag = true;


    public Room() {}

    @Builder
    public Room(User user, String roomName) {
        this.user = user;
        this.roomName = roomName;
    }

    public void delete() {
        this.flag = false;
    }
}
