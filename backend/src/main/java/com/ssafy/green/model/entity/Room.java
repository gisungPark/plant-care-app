package com.ssafy.green.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Table(name="Room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id @GeneratedValue
    @Column(name = "r_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private User user;
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
