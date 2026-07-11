package com.Project_01.EdTech.Controller;

import com.Project_01.EdTech.DTOs.response.EnrollmentResponseDTO;
import com.Project_01.EdTech.Entity.Enrollment;
import com.Project_01.EdTech.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/{courseId}")
    public ResponseEntity<?> enrollInCourse(@PathVariable Long courseId) {

        Enrollment enrollment=enrollmentService.enrollinCourse(courseId);

        EnrollmentResponseDTO enrollmentResponseDTO=new EnrollmentResponseDTO();
        enrollmentResponseDTO.setCourseName(enrollment.getCourseId().getCourseName());
        enrollmentResponseDTO.setId(enrollment.getId());
        enrollmentResponseDTO.setEnrolledAt(enrollment.getEnrolledAt());
        enrollmentResponseDTO.setProgress(enrollment.getProgress());
        enrollmentResponseDTO.setCourseId(enrollment.getCourseId().getId());
        return new ResponseEntity<>(enrollmentResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/my-enrollments")
    public ResponseEntity<List<EnrollmentResponseDTO>> getMyEnrollments() {
        List<EnrollmentResponseDTO> enrollmentResponseDTOList=new ArrayList<>();
       List<Enrollment> enrollmentList=enrollmentService.getMyEnrollments();

       for(Enrollment enrollment:enrollmentList){
           EnrollmentResponseDTO enrollmentResponseDTO=new EnrollmentResponseDTO();
           enrollmentResponseDTO.setCourseId(enrollment.getCourseId().getId());
           enrollmentResponseDTO.setEnrolledAt(enrollment.getEnrolledAt());
           enrollmentResponseDTO.setProgress(enrollment.getProgress());
           enrollmentResponseDTO.setStudentName(enrollment.getStudent().getUserName());
           enrollmentResponseDTO.setCourseName(enrollment.getCourseId().getCourseName());
           enrollmentResponseDTO.setId(enrollment.getId());

           enrollmentResponseDTOList.add(enrollmentResponseDTO);
       }

        return new ResponseEntity<>(
                enrollmentResponseDTOList,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> unEnroll(@PathVariable Long courseId) {

        enrollmentService.unenroll(courseId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
/*
    @PutMapping("/progress/{courseId}/{progress}")
    public ResponseEntity<Enrollment> updateProgress(
            @PathVariable Long courseId,
            @PathVariable Integer progress) {

        return new ResponseEntity<>(
                enrollmentService.updateProgress(courseId, progress),
                HttpStatus.OK
        );
    }

 */

    @GetMapping("/check/{courseId}")
    public ResponseEntity<Boolean> isEnrolled(@PathVariable Long courseId) {

        return new ResponseEntity<>(
                enrollmentService.isEnrolled(courseId),
                HttpStatus.OK
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getStudentsByCourse(
            @PathVariable Long courseId) {

        return new ResponseEntity<>(
                enrollmentService.getStudentsByCourse(courseId),
                HttpStatus.OK
        );
    }

    @PutMapping("/progress/{courseId}/{lectureId}")
    public ResponseEntity<EnrollmentResponseDTO> updateProgress(

            @PathVariable Long courseId,

            @PathVariable Long lectureId

    ) {

        return ResponseEntity.ok(

                enrollmentService.updateProgress1(
                        courseId,
                        lectureId
                )

        );

    }
}