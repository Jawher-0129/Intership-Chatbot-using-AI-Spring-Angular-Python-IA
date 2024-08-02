package com.example.StageTalanChat.controllers;

import com.example.StageTalanChat.entities.User;
import com.example.StageTalanChat.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class UserController {
  private final PasswordEncoder passwordEncoder;

  private final UserService userService;

  @GetMapping("/users")
  public List<User> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUser(@PathVariable long id) {
    return userService.getUserById(id)
      .map(user -> ResponseEntity.ok(user))
      .orElseThrow(() -> new EntityNotFoundException("Request not found"));
  }

  @PostMapping("/adduser")
  public User addUser(@RequestBody User user) {
    return userService.save(user);
  }

  @PutMapping("user/{id}")
  public ResponseEntity<?> addUser(@RequestBody User user, @PathVariable long id)
  {
    if(userService.existById(id))
    {
      User user1=userService.getUserById(id)
        .orElseThrow (()-> new EntityNotFoundException("Request not found"));
      user1.setNom(user.getNom());
      user1.setPrenom(user.getPrenom());
      user1.setEmail(user.getEmail());
      String hashpwd=passwordEncoder.encode(user.getPassword());
      user1.setPassword(hashpwd);
      user1.setRole(user.getRole());
      userService.save(user1);
      return ResponseEntity.ok().body(user1);
    }
    else
    {
      HashMap<String,String> message=new HashMap<>();
      message.put("message ",id+" user not found or matched");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
  }

  @DeleteMapping("/deleteuser/{id}")
  public ResponseEntity<?> deleteuser(@PathVariable long id)
  {
    if(userService.existById(id))
    {
      userService.deleteuser(id);
      HashMap<String,String> message=new HashMap<>();
      message.put("message",id+" User deleted with succefuly");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    else
    {
      HashMap<String,String> message=new HashMap<>();
      message.put("message ",id+"N'existe pas");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
  }
}
