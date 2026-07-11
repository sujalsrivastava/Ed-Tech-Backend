package com.Project_01.EdTech.DTOs.response;

import com.Project_01.EdTech.EnumsClasses.Roles;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private String userName;
    private String role;

}
