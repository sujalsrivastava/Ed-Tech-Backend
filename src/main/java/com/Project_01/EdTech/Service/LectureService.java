package com.Project_01.EdTech.Service;

import com.Project_01.EdTech.DTOs.request.LectureDTO;
import com.Project_01.EdTech.DTOs.response.LectureResponseDTO;
import com.Project_01.EdTech.Entity.Course;
import com.Project_01.EdTech.Entity.Lecture;
import com.Project_01.EdTech.Entity.User;
import com.Project_01.EdTech.EnumsClasses.Roles;
import com.Project_01.EdTech.Repository.CourseRepository;
import com.Project_01.EdTech.Repository.LectureRepository;
import com.Project_01.EdTech.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LectureService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    private LectureResponseDTO mapToLectureResponse(Lecture lecture) {

        LectureResponseDTO dto = new LectureResponseDTO();

        dto.setId(lecture.getId());
        dto.setTitle(lecture.getTitle());
        dto.setURL(lecture.getURL());
        dto.setCourseId(lecture.getCourse().getId());

        return dto;
    }

    public Lecture addLecture(Long id, String title, MultipartFile video) throws IOException {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();

        String username=auth.getName();
        User user=userRepository.findByuserName(username);

        if(user.getRole()!= Roles.INSTRUCTOR){
            throw new RuntimeException("Acccess Denied");
        }


        Lecture lecture1=new Lecture();

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (course.getInstructor().getId()!=(user.getId())) {
            throw new RuntimeException("ACCESS DENIED");
        }
       lecture1.setCourse(course);
        String videoUrl=cloudinaryService.uploadVideo(video);
       lecture1.setTitle(title);
       lecture1.setURL(videoUrl);

       Lecture lecture2=lectureRepository.save(lecture1);

       return lecture2;
    }

    public Lecture updateLecture(Long lectureId, String title) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User user = userRepository.findByuserName(username);

        if (user.getRole() != Roles.INSTRUCTOR) {
            throw new RuntimeException("ACCESS DENIED");
        }

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        Course course = lecture.getCourse();

        if (course.getInstructor().getId()!=(user.getId())) {
            throw new RuntimeException("ACCESS DENIED");
        }

        lecture.setTitle(title);

        return lectureRepository.save(lecture);
    }

    public void deleteLecture(Long lectureId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User user = userRepository.findByuserName(username);

        if (user.getRole() != Roles.INSTRUCTOR) {
            throw new RuntimeException("ACCESS DENIED");
        }

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        Course course = lecture.getCourse();

        if (course.getInstructor().getId()!=(user.getId())) {
            throw new RuntimeException("ACCESS DENIED");
        }

        lectureRepository.delete(lecture);
    }
    public LectureResponseDTO getLectureById(Long lectureId) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        return mapToLectureResponse(lecture);
    }
    public List<LectureResponseDTO> getAllLecturesByCourse(Long courseId) {

        List<Lecture> lectures = lectureRepository.findByCourse_Id(courseId);

        List<LectureResponseDTO> response = new ArrayList<>();

        for (Lecture lecture : lectures) {
            response.add(mapToLectureResponse(lecture));
        }

        return response;
    }
}
