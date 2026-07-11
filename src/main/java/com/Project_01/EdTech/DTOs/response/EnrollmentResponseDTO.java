package com.Project_01.EdTech.DTOs.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrollmentResponseDTO {

    private Long id;

    private String studentName;

    private String courseName;

    private Double progress;

    private LocalDate enrolledAt;

    private Long courseId;
}