package com.Project_01.EdTech.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String URL;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


}
