package com.Project_01.EdTech.DTOs.response;

import com.Project_01.EdTech.EnumsClasses.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;

    private String userName;

    private String email;

    private Roles role;
}