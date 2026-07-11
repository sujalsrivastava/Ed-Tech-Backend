package com.Project_01.EdTech.Repository;

import com.Project_01.EdTech.Entity.CompletedLecture;
import com.Project_01.EdTech.Entity.Enrollment;
import com.Project_01.EdTech.Entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedLectureRepo extends JpaRepository< CompletedLecture,Long> {

    boolean existsByEnrollmentAndLecture(
            Enrollment enrollment,
            Lecture lecture
    );

    long countByEnrollment(Enrollment enrollment);
}
