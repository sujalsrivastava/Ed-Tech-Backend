package com.Project_01.EdTech.DTOs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponseDTO {

    private Long id;

    private String courseName;

    private String description;

    private Double price;

    private String instructorName;
}