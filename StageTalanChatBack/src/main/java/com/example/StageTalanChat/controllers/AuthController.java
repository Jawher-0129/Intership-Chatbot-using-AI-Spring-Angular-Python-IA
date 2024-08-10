package com.example.StageTalanChat.controllers;
import com.example.StageTalanChat.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private PasswordResetService passwordResetService;

  @PostMapping("/forgot-password")
  public void forgotPassword(@RequestBody Map<String, String> payload) {
    String email = payload.get("email");
    passwordResetService.sendPasswordResetEmail(email);
  }

  @PostMapping("/reset-password")
  public void resetPassword(@RequestBody Map<String, String> payload) {
    String token = payload.get("token");
    String newPassword = payload.get("newPassword");
    passwordResetService.resetPassword(token, newPassword);
  }




}

