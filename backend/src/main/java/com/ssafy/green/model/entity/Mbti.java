package com.ssafy.green.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mbti")
public class Mbti {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String opt1;
    private String val1;
    private String opt2;
    private String val2;

    @Builder
    public Mbti(String question, String opt1, String val1, String opt2, String val2){
        this.question = question;
        this.opt1 = opt1;
        this.val1 = val1;
        this.opt2 = opt2;
        this.val2 = val2;
    }
}
