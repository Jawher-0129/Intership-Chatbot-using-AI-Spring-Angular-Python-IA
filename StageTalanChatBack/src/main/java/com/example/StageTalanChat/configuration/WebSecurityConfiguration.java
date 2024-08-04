package com.example.StageTalanChat.configuration;

import com.example.StageTalanChat.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {


  private final JwtRequestFilter jwtRequestFilter;


  @Autowired
  public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/signup/p1").permitAll();
            auth.requestMatchers("/login/p2").permitAll();
            auth.requestMatchers("/api/v1/users/**").permitAll();
            auth.requestMatchers("/api/v1/user/**").permitAll();
            auth.requestMatchers("/api/v1/deleteuser/**").permitAll();
            auth.requestMatchers("/api/v1/adduser").permitAll();
            auth.requestMatchers("/api/hello").permitAll();
            auth.anyRequest().authenticated(); // Ensure this line is called for other requests
          })
          .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          )
          .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Place this outside of authorizeHttpRequests
        return http.build();
      }



  @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }
}

