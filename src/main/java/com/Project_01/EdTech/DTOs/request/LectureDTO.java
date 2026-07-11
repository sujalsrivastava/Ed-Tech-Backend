package com.Project_01.EdTech.DTOs.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LectureDTO {

    private String title;

    private MultipartFile video;
}