package com.example.StageTalanChat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
  private String jwtToken;
}
