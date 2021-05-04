package com.ssafy.green.model.entity.plant;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name="water")
public class Water {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "pid")
    @NonNull
    private PlantCare plantCare;

    private String waterDate;

    @Builder
    public Water(PlantCare plantCare, String waterDate) {
        this.plantCare = plantCare;
        this.waterDate = waterDate;
    }
}