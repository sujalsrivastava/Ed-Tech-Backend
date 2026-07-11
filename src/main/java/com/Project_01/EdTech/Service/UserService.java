package com.Project_01.EdTech.Service;

import com.Project_01.EdTech.DTOs.request.UserDtoforLoginSignup;
import com.Project_01.EdTech.DTOs.response.LoginResponse;
import com.Project_01.EdTech.Entity.User;
import com.Project_01.EdTech.EnumsClasses.Roles;
import com.Project_01.EdTech.Repository.UserRepository;
import com.Project_01.EdTech.jwtConfig.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceIMPL userDetailServiceIMPL;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(UserDtoforLoginSignup user){
            if(userRepository.existsByuserName(user.getUserName())){
                throw new RuntimeException("Username already exist");
            }
            if (userRepository.existsByemail(user.getEmail())) {
                throw new RuntimeException("Email already exist");
            }
            if(user.getRole()==Roles.ADMIN){
                throw new RuntimeException("User cannot become admin");
            }
            User newUser=new User();
            newUser.setUserName(user.getUserName());
            newUser.setPassWord(passwordEncoder.encode(user.getPassWord()));
            newUser.setEmail(user.getEmail());

            newUser.setRole(Roles.valueOf(user.getRole().toString().toUpperCase()));
        System.out.println(newUser.getRole());
            userRepository.save(newUser);
    }

    public LoginResponse login(UserDtoforLoginSignup user){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassWord()));
            UserDetails userDetails=userDetailServiceIMPL.loadUserByUsername(user.getUserName());
            if(userDetails!=null){
                String jwtToken=jwtUtility.generateToken(userDetails.getUsername());
                User dbUser = userRepository.findByuserName(user.getUserName());

                LoginResponse response = new LoginResponse();

                response.setToken(jwtToken);
                response.setUserName(dbUser.getUserName());
                response.setRole(dbUser.getRole().toString());

                return response;
            }
        return null;
    }
}