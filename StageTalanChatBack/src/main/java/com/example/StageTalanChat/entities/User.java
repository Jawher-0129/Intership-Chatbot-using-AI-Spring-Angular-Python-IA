package com.example.StageTalanChat.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String role;
    @Column(name = "reset_token")
    private String resetToken;

  public User(String nom, String prenom, String email, String password, String role) {
    this.nom = nom;
    this.prenom = prenom;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
