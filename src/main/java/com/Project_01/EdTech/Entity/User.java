package com.Project_01.EdTech.Entity;

import com.Project_01.EdTech.EnumsClasses.Roles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false)
    private String userName;

    private String passWord;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "instructor")
    private List<Course> courseList=new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollmentList=new ArrayList<>();

}
