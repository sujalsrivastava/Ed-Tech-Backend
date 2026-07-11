package com.Project_01.EdTech.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    private String description;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User instructor;

    @OneToMany(mappedBy = "course",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<Lecture> lectureList=new ArrayList<>();

    @OneToMany(mappedBy = "courseId",cascade = CascadeType.ALL
    ,orphanRemoval = true)
    private List<Enrollment> enrollmentList=new ArrayList<>();
}
