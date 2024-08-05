package com.example.StageTalanChat.configuration;

import com.example.StageTalanChat.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration implements WebMvcConfigurer {

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
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> {
        auth.requestMatchers("/signup/p1").permitAll();
        auth.requestMatchers("/login/p2").permitAll();
        auth.requestMatchers("/login/google").permitAll();
        auth.requestMatchers("/api/auth/forgot-password").permitAll();
        auth.requestMatchers("/api/auth/reset-password").permitAll();
        auth.requestMatchers("/api/v1/users/**").permitAll();
        auth.requestMatchers("/api/v1/user/**").permitAll();
        auth.requestMatchers("/api/v1/deleteuser/**").permitAll();
        auth.requestMatchers("/api/v1/adduser").permitAll();
        auth.requestMatchers("/api/hello").permitAll();
        auth.requestMatchers("/api/auth/forgot-password").permitAll();
        auth.requestMatchers("/api/auth/reset-password").permitAll();
        auth.requestMatchers("/oauth2/**").permitAll(); // Assurez-vous que ce matcher est correct
        auth.anyRequest().authenticated();
      })
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      /*.oauth2Login(oauth2 -> oauth2
        .loginPage("/login")
        .defaultSuccessUrl("/home", true)
        .failureUrl("/login?error")
      )*/
      .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:4200")
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .allowedHeaders("*")
      .allowCredentials(true);
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}




