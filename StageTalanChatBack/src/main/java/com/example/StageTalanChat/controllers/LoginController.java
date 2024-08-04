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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
}
