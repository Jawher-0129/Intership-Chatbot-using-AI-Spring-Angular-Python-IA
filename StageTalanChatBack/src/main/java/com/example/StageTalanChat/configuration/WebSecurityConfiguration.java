package com.example.StageTalanChat.configuration;

import com.example.StageTalanChat.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
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
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> {
        auth.requestMatchers("/signup/p1").permitAll();
        auth.requestMatchers("/login/p2").permitAll();
        auth.requestMatchers("/login/google").permitAll();

        auth.requestMatchers("/api/v1/users/**").permitAll();
        auth.requestMatchers("/api/v1/user/**").permitAll();
        auth.requestMatchers("/api/v1/deleteuser/**").permitAll();
        auth.requestMatchers("/api/v1/adduser").permitAll();
        auth.requestMatchers("/api/hello").permitAll();
        auth.requestMatchers("/oauth2/**").permitAll(); // Permet l'accès aux endpoints OAuth2
        auth.anyRequest().authenticated(); // Assurez-vous que les autres requêtes sont authentifiées
      })
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .oauth2Login(oauth2 -> oauth2
        .loginPage("/login") // URL de la page de connexion personnalisée
        .defaultSuccessUrl("/login/success", true) // URL de redirection après une connexion réussie
        .failureUrl("/login?error") // URL de redirection en cas d'échec de connexion
      )
      .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Place le filtre JWT avant le filtre d'authentification par mot de passe

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
