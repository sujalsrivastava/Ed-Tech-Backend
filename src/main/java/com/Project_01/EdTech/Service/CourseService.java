package com.Project_01.EdTech.Service;

import com.Project_01.EdTech.DTOs.response.CourseResponseDTO;
import com.Project_01.EdTech.Entity.Course;
import com.Project_01.EdTech.Entity.User;
import com.Project_01.EdTech.EnumsClasses.Roles;
import com.Project_01.EdTech.Repository.CourseRepository;
import com.Project_01.EdTech.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private CourseResponseDTO mapToCourseResponse(Course course) {

        CourseResponseDTO dto = new CourseResponseDTO();

        dto.setId(course.getId());
        dto.setCourseName(course.getCourseName());
        dto.setDescription(course.getDescription());
        dto.setPrice(course.getPrice());
        dto.setInstructorName(course.getInstructor().getUserName());

        return dto;
    }

    public Course createCourse(Course course){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();

        String username=auth.getName();
        User user=userRepository.findByuserName(username);

        if(user.getRole()!= Roles.INSTRUCTOR){
            throw new RuntimeException("ACCESS DENIED");
        }

        course.setInstructor(user);
        return courseRepository.save(course);
    }

    public Course updateCourse(Long courseId, Course updatedCourse){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User instructor = userRepository.findByuserName(username);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (course.getInstructor().getId()!=(instructor.getId())) {
            throw new RuntimeException("You can update only your own courses.");
        }
        course.setCourseName(updatedCourse.getCourseName());
        course.setDescription(updatedCourse.getDescription());
        course.setPrice(updatedCourse.getPrice());

        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User instructor = userRepository.findByuserName(username);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (course.getInstructor().getId()!=(instructor.getId())) {
            throw new RuntimeException("ACCESS DENIED");
        }

        courseRepository.delete(course);
    }
    public List<CourseResponseDTO> getmyCourses() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        List<Course> courses = courseRepository.findByInstructor_userName(username);

        List<CourseResponseDTO> response = new ArrayList<>();

        for (Course course : courses) {

            CourseResponseDTO dto = new CourseResponseDTO();
            response.add(mapToCourseResponse(course));
        }

        return response;
    }
    public List<CourseResponseDTO> getAllCourses() {

        List<Course> courses = courseRepository.findAll();

        List<CourseResponseDTO> responseList = new ArrayList<>();

        for (Course course : courses) {
            responseList.add(mapToCourseResponse(course));
        }

        return responseList;
    }
public CourseResponseDTO getCourse(Long id){
        Course course=courseRepository.findById(id).orElseThrow(()->new RuntimeException("Coure not found!"));
        CourseResponseDTO courseResponseDTO=mapToCourseResponse(course);
        return courseResponseDTO;
}
}
