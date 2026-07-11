package com.Project_01.EdTech.Controller;

import com.Project_01.EdTech.Entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserController {
   @GetMapping("/test")
   public String test(){
        return "JWT IS WORKING";
    }
}
