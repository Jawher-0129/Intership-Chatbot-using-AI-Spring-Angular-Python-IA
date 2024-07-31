package com.example.StageTalanChat.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String nom;
    private String prenom;
    private String password;
    private String role;
}
