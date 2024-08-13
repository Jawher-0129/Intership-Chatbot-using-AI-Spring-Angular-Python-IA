package com.example.StageTalanChat.services;
import com.example.StageTalanChat.entities.User;
import com.example.StageTalanChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;


@Service
public class PasswordResetService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailService emailService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public void sendPasswordResetEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email n'existe pas"));
    if (user == null) {
      throw new RuntimeException("Email introuvable");
    }
    String token = UUID.randomUUID().toString();
    System.out.println(token);
    user.setResetToken(token);
    userRepository.save(user);

    String resetUrl = "http://localhost:4200/resetpassword?token=" + token;
   emailService.sendEmail(user.getEmail(), "Mot de passe oublié", "Pour changé votre mot de passe , cliquez sur le lien ci dessous:\n" + resetUrl);
  }

  public void resetPassword(String token, String newPassword) {
    User user = userRepository.findByResetToken(token).orElseThrow(() -> new RuntimeException("invalid token"));
    if (user == null) {
      throw new RuntimeException("Invalid token");
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    user.setResetToken(null);
    userRepository.save(user);
  }
}

