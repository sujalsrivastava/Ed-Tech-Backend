package com.Project_01.EdTech.Controller;

import com.Project_01.EdTech.DTOs.request.UserDtoforLoginSignup;
import com.Project_01.EdTech.DTOs.response.LoginResponse;
import com.Project_01.EdTech.Entity.User;
import com.Project_01.EdTech.Repository.UserRepository;
import com.Project_01.EdTech.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody UserDtoforLoginSignup user){
       try{
           userService.signup(user);
           return new ResponseEntity<>(true,HttpStatus.CREATED);
       }
       catch (Exception e){
           return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserDtoforLoginSignup user){
        LoginResponse response=userService.login(user);
        return response;
    }
}
