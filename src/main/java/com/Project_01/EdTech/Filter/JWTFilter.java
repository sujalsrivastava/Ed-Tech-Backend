package com.Project_01.EdTech.Filter;

import com.Project_01.EdTech.Service.UserDetailServiceIMPL;
import com.Project_01.EdTech.jwtConfig.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceIMPL userDetailServiceIMPL;

    @Autowired
    private JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizedheader=request.getHeader("Authorization");
        String username=null;
        String jwt=null;

        if(authorizedheader!=null&&authorizedheader.startsWith("Bearer ")){
            jwt=authorizedheader.substring(7);
            username=jwtUtility.extractUsername(jwt);
        }
        if(username!=null){
            UserDetails user=userDetailServiceIMPL.loadUserByUsername(username);
            if(jwtUtility.validateToken(jwt)){
                UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);
    }
}
