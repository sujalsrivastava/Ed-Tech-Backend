package com.Project_01.EdTech.Repository;

import com.Project_01.EdTech.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> findByInstructor_userName(String userName);
}
