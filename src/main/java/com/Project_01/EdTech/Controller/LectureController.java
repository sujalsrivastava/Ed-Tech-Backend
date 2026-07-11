package com.Project_01.EdTech.Controller;

import com.Project_01.EdTech.DTOs.request.LectureDTO;
import com.Project_01.EdTech.DTOs.response.LectureResponseDTO;
import com.Project_01.EdTech.Entity.Lecture;
import com.Project_01.EdTech.Service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lecture")
public class LectureController {
    @Autowired
    private LectureService lectureService;
    @PostMapping("/{id}")
    public ResponseEntity<?> addLecture(@PathVariable Long id,
                                        @ModelAttribute LectureDTO lectureDTO) throws IOException {
       return new ResponseEntity<>(lectureService.addLecture(id,lectureDTO.getTitle(),lectureDTO.getVideo()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLecure(@PathVariable Long id, @RequestBody String title){
        return new ResponseEntity<>(lectureService.updateLecture(id,title), HttpStatus.OK);
    }

    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Boolean> deleteLecture(@PathVariable Long lectureId) {

        lectureService.deleteLecture(lectureId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/{lectureId}")
    public ResponseEntity<LectureResponseDTO> getLectureById(@PathVariable Long lectureId) {

        return new ResponseEntity<>(
                lectureService.getLectureById(lectureId),
                HttpStatus.OK
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LectureResponseDTO>> getAllLecturesByCourse(@PathVariable Long courseId) {

        return new ResponseEntity<>(
                lectureService.getAllLecturesByCourse(courseId),
                HttpStatus.OK
        );
    }
}
