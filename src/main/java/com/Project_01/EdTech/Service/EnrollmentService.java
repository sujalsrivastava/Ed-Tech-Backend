package com.Project_01.EdTech.Service;

import com.Project_01.EdTech.DTOs.response.EnrollmentResponseDTO;
import com.Project_01.EdTech.Entity.*;
import com.Project_01.EdTech.EnumsClasses.Roles;
import com.Project_01.EdTech.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CompletedLectureRepo completedLectureRepo;

    @Autowired
    private LectureRepository lectureRepository;

    private EnrollmentResponseDTO mapToDTO(
            Enrollment enrollment
    ){

        EnrollmentResponseDTO dto =
                new EnrollmentResponseDTO();

        dto.setId(enrollment.getId());

        dto.setCourseId(
                enrollment.getCourseId().getId()
        );

        dto.setCourseName(
                enrollment.getCourseId().getCourseName()
        );

        dto.setStudentName(
                enrollment.getStudent().getUserName()
        );

        dto.setEnrolledAt(
                enrollment.getEnrolledAt()
        );

        dto.setProgress(
                enrollment.getProgress()
        );

        return dto;

    }

    private User getCuurentUser(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String username=auth.getName();
        User user=userRepository.findByuserName(username);
        return user;
    }

    public Enrollment enrollinCourse(Long courseId){
        User user=getCuurentUser();

        if(user.getRole()!= Roles.STUDENT){
            throw new RuntimeException("Not authorized to perform this action!Try logging in with Student");
        }
        Course course=courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByStudentAndCourseId(user, course)) {
            throw new RuntimeException("You are already enrolled in this course");
        }
        Enrollment enrollment=new Enrollment();

        enrollment.setEnrolledAt(LocalDate.now());
        enrollment.setCourseId(course);
        enrollment.setStudent(user);
        enrollment.setProgress(0);

        return enrollmentRepository.save(enrollment);

    }

    public List<Enrollment> getMyEnrollments() {

        User user = getCuurentUser();

        if (user.getRole() != Roles.STUDENT) {
            throw new RuntimeException("ACCESS DENIED");
        }

        return enrollmentRepository.findByStudent(user);
    }

    public void unenroll(Long courseId) {

        User student = getCuurentUser();

        if (student.getRole() != Roles.STUDENT) {
            throw new RuntimeException("ACCESS DENIED");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = enrollmentRepository
                .findByStudentAndCourseId(student, course)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollmentRepository.delete(enrollment);
    }

    public Enrollment updateProgress(Long courseId, Integer progress) {

        User student = getCuurentUser();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = enrollmentRepository
                .findByStudentAndCourseId(student, course)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setProgress(progress);

        return enrollmentRepository.save(enrollment);
    }

    public boolean isEnrolled(Long courseId) {

        User student = getCuurentUser();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return enrollmentRepository.existsByStudentAndCourseId(student, course);
    }

    public List<Enrollment> getStudentsByCourse(Long courseId) {

        User instructor = getCuurentUser();

        if (instructor.getRole() != Roles.INSTRUCTOR) {
            throw new RuntimeException("ACCESS DENIED");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (course.getInstructor().getId()!=(instructor.getId())) {
            throw new RuntimeException("ACCESS DENIED");
        }

        return enrollmentRepository.findByCourseId(course);
    }

    @Transactional
    public EnrollmentResponseDTO updateProgress1(
            Long courseId,
            Long lectureId
    ) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        User student = userRepository
                .findByuserName(username);

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course Not Found"));

        Lecture lecture = lectureRepository
                .findById(lectureId)
                .orElseThrow(() ->
                        new RuntimeException("Lecture Not Found"));

        Enrollment enrollment = enrollmentRepository
                .findByStudentAndCourseId(student, course)
                .orElseThrow(() ->
                        new RuntimeException("Enrollment Not Found"));

        // Prevent duplicate completion
        boolean alreadyCompleted =
                completedLectureRepo
                        .existsByEnrollmentAndLecture(
                                enrollment,
                                lecture
                        );

        if (alreadyCompleted) {

            return mapToDTO(enrollment);

        }

        CompletedLecture completedLecture =
                new CompletedLecture();

        completedLecture.setEnrollment(enrollment);

        completedLecture.setLecture(lecture);

        completedLectureRepo.save(completedLecture);

        long completedLectures =
                completedLectureRepo
                        .countByEnrollment(enrollment);

        long totalLectures =
                lectureRepository
                        .countByCourse(course);

        double progress =
                ((double) completedLectures / totalLectures) * 100;

        enrollment.setProgress(progress);

        enrollmentRepository.save(enrollment);

        return mapToDTO(enrollment);

    }
}
