package com.Project_01.EdTech.Entity;

import com.Project_01.EdTech.Entity.Enrollment;
import com.Project_01.EdTech.Entity.Lecture;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class CompletedLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Enrollment enrollment;

    @ManyToOne
    private Lecture lecture;

}