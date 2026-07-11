package com.Project_01.EdTech.Security;

import com.Project_01.EdTech.Filter.JWTFilter;
import com.Project_01.EdTech.Service.UserDetailServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailServiceIMPL userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable());
        return http.authorizeHttpRequests(request -> request

                .requestMatchers("/auth/**")
                .permitAll()

                // Course
                .requestMatchers(HttpMethod.POST, "/course/create-course")
                .hasRole("INSTRUCTOR")

                .requestMatchers(HttpMethod.PUT, "/course/update-course/**")
                .hasRole("INSTRUCTOR")

                .requestMatchers(HttpMethod.DELETE, "/course/delete-course/**")
                .hasRole("INSTRUCTOR")

                // Lecture
                .requestMatchers(HttpMethod.POST, "/lecture/**")
                .hasRole("INSTRUCTOR")

                .requestMatchers(HttpMethod.PUT, "/lecture/**")
                .hasRole("INSTRUCTOR")

                .requestMatchers(HttpMethod.DELETE, "/lecture/**")
                .hasRole("INSTRUCTOR")

                // Enrollment
                .requestMatchers(HttpMethod.POST, "/enrollment/**")
                .hasRole("STUDENT")

                .requestMatchers(HttpMethod.PUT, "/enrollment/progress/**")
                .hasRole("STUDENT")

                .requestMatchers(HttpMethod.DELETE, "/enrollment/**")
                .hasRole("STUDENT")

                .anyRequest()
                .authenticated()

        ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("https://ed-tech-backend-production-1705.up.railway.app/"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
