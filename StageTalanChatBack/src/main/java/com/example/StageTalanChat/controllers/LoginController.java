package com.example.StageTalanChat.controllers;

import com.example.StageTalanChat.dto.LoginRequest;
import com.example.StageTalanChat.dto.LoginResponse;
import com.example.StageTalanChat.dto.SignupRequest;
import com.example.StageTalanChat.entities.User;
import com.example.StageTalanChat.services.UserService;
import com.example.StageTalanChat.services.jwt.UserServiceImpl;
import com.example.StageTalanChat.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/login")
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final UserServiceImpl userService;
  private final UserService userService1;
  private final JwtUtil jwtUtil;


  @Autowired
  public LoginController(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtUtil jwtUtil,UserService userService1) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.jwtUtil = jwtUtil;
    this.userService1=userService1;
  }

  @PostMapping("/p2")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
      );
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserDetails userDetails;
    User user;
    try {
      userDetails = userService.loadUserByUsername(loginRequest.getEmail());
      user=userService1.getUserByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    String jwt = jwtUtil.generateToken(userDetails.getUsername());
    LoginResponse loginResponse = new LoginResponse(jwt, user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), user.getRole());
    return ResponseEntity.ok(loginResponse);
  }


  @PostMapping("/google")
  public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body) {
    String idToken = body.get("idToken");
    return ResponseEntity.ok("Google login successful");
  }






}
