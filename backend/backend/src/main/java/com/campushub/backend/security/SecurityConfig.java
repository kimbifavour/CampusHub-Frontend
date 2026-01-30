//package com.campushub.backend.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable()) // optional, useful if you plan JWT later
//                .formLogin(form -> form
//                        .loginPage("/login")   // custom login page
//                        .permitAll()
//                )
//                .authorizeHttpRequests(auth -> auth
//                        // Public endpoints
//                        .requestMatchers("/req/signup").permitAll()
//                        // Swagger endpoints
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
//                        // Everything else requires authentication
//                        .anyRequest().authenticated()
//                )
//                .build();
//    }
//}
//
////will change to implement jwt authentication later