package com.example.StageTalanChat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
  private String jwtToken;
  private Long id;
  private String nom;
  private String prenom;
  private String email;
  private String role;

}
