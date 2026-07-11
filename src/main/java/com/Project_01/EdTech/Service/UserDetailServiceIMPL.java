package com.Project_01.EdTech.Service;

import com.Project_01.EdTech.Entity.User;
import com.Project_01.EdTech.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceIMPL implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByuserName(username);
        if(user!=null) {
            UserDetails u = org.springframework.security.core.userdetails.User.builder().username(user.getUserName())
                    .password(user.getPassWord()).roles(user.getRole().toString().toUpperCase())
                    .build();

            return u;
        }
        throw new UsernameNotFoundException("username not found");//
    }

}
