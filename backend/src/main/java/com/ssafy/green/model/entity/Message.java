package com.ssafy.green.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    private String title;
    private String content;
    private LocalDateTime dateTime;

    @Column(name = "flag", columnDefinition = "boolean default true")
    private Boolean flag = true;

    public Message(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.dateTime = LocalDateTime.now();
    }

    public void check(){
        this.flag = false;
    }
}
