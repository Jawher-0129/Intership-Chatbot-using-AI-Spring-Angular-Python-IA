package com.example.StageTalanChat.services;
import org.springframework.data.domain.Pageable;

import com.example.StageTalanChat.entities.User;
import com.example.StageTalanChat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public List<User> getUsers() {
    return userRepository.findAll();
  }
  public Optional<User> getUserById(long id)
  {
    return userRepository.findById(id);
  }

  public User save(User user) {
    user.setRole("CLIENT");
    String hash=passwordEncoder.encode(user.getPassword());
    user.setPassword(hash);
    return userRepository.saveAndFlush(user);
  }

  public boolean existById(long id)
  {
    return userRepository.existsById(id);
  }

  public void deleteuser(long id)
  {
    userRepository.deleteById(id);
  }

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
  public void incrementUserActive(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    user.setActive(user.getActive() + 1);
    userRepository.save(user);
  }

  public List<User> getTopActiveUsers() {
    Pageable pageable = PageRequest.of(0, 3);
    return userRepository.findTopActiveUsers(pageable);
  }


}
