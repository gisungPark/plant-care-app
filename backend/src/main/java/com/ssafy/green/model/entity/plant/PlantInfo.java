package com.ssafy.green.model.entity.plant;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name="plant_info")
public class PlantInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String common;
    private String name;
    private String category;
    private String level;
    private boolean smell;
    private boolean air;
    private String temp;
    private String humid;
    private String water;
    private String info;
    private String image;
}
