package com.Project_01.EdTech.DTOs.request;

import com.Project_01.EdTech.EnumsClasses.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoforLoginSignup {

    private String userName;

    private String passWord;

    private String email;

    private Roles role;
}
