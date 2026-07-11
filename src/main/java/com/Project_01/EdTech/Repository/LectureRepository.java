package com.Project_01.EdTech.Repository;

import com.Project_01.EdTech.Entity.Course;
import com.Project_01.EdTech.Entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture,Long> {
    List<Lecture> findByCourse_Id(Long courseId);
    long countByCourse(Course course);
}
