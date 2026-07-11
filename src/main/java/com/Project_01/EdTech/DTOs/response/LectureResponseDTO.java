package com.Project_01.EdTech.DTOs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureResponseDTO {

    private Long id;

    private String title;

    private String URL;

    private Long courseId;
}