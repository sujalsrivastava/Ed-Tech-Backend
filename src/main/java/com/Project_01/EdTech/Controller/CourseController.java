package com.Project_01.EdTech.Controller;


import com.Project_01.EdTech.DTOs.request.CourseDTO;
import com.Project_01.EdTech.DTOs.response.CourseResponseDTO;
import com.Project_01.EdTech.Entity.Course;
import com.Project_01.EdTech.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create-course")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO course){

        Course course1=new Course();
        course1.setCourseName(course.getCourseName());
        course1.setDescription(course.getDescription());
        course1.setPrice(course.getPrice());
        Course dtomapping=courseService.createCourse(course1);

        CourseResponseDTO courseResponseDTO=new CourseResponseDTO();
        courseResponseDTO.setCourseName(dtomapping.getCourseName());
        courseResponseDTO.setId(dtomapping.getId());
        courseResponseDTO.setDescription(dtomapping.getDescription());
        courseResponseDTO.setPrice(dtomapping.getPrice());
        courseResponseDTO.setInstructorName(dtomapping.getInstructor().getUserName());
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update-course-details/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long id){
        Course course1=new Course();
        course1.setCourseName(courseDTO.getCourseName());
        course1.setDescription(courseDTO.getDescription());
        course1.setPrice(courseDTO.getPrice());
        Course c1=courseService.updateCourse(id,course1);
        CourseResponseDTO courseResponseDTO=new CourseResponseDTO();
        courseResponseDTO.setCourseName(c1.getCourseName());
        courseResponseDTO.setDescription(c1.getDescription());
        courseResponseDTO.setPrice(c1.getPrice());
        courseResponseDTO.setId(c1.getId());
        courseResponseDTO.setInstructorName(c1.getInstructor().getUserName());
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete-course/{id}")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable Long id) {

        courseService.deleteCourse(id);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<List<CourseResponseDTO>> getMyCourses() {

        List<CourseResponseDTO> courseList = courseService.getmyCourses();

        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @GetMapping("/all-courses")
    public ResponseEntity<List<CourseResponseDTO>> getAllcourses() {

        List<CourseResponseDTO> courseList = courseService.getAllCourses();

        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @GetMapping("/my-course/{id}")
    public ResponseEntity<CourseResponseDTO> getAllcourses(@PathVariable Long id) {

        CourseResponseDTO courseResponseDTO=courseService.getCourse(id);

        return new ResponseEntity<>(courseResponseDTO, HttpStatus.OK);
    }



}
