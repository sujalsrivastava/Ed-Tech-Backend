package com.Project_01.EdTech.Repository;

import com.Project_01.EdTech.Entity.Course;
import com.Project_01.EdTech.Entity.Enrollment;
import com.Project_01.EdTech.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    boolean existsByStudentAndCourseId(User Student, Course course);

    List<Enrollment> findByStudent(User Student);

    List<Enrollment> findByCourseId(Course course);

    Optional<Enrollment> findByStudentAndCourseId(User Student, Course course);
}
