package com.example.StageTalanChat.controllers;

import com.example.StageTalanChat.entities.User;
import com.example.StageTalanChat.services.PdfService;
import com.example.StageTalanChat.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class UserController {
  private final PasswordEncoder passwordEncoder;

  private final UserService userService;

  private final PdfService pdfService;

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

  @GetMapping("/download-users-pdf")
  public ResponseEntity<InputStreamResource> downloadUsersPdf() {
    List<User> users = userService.getUsers();
    ByteArrayInputStream bis = pdfService.generatePdf(users);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=users.pdf");

    return ResponseEntity
      .ok()
      .headers(headers)
      .contentType(MediaType.APPLICATION_PDF)
      .body(new InputStreamResource(bis));
  }

  @PostMapping("/chatbot")
  public Map<String, String> chatbotResponse(@RequestBody Map<String, String> payload) {
    RestTemplate restTemplate = new RestTemplate();
    String flaskApiUrl = "http://localhost:5000/chatbot";

    ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, payload, Map.class);
    return response.getBody();
  }

  @GetMapping("/top-active")
  public List<User> getTopActiveUsers() {
    return userService.getTopActiveUsers();
  }



}
