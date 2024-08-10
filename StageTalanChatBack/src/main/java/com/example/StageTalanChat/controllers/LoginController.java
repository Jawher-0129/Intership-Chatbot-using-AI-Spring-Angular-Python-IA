package com.example.StageTalanChat.controllers;

import com.example.StageTalanChat.dto.LoginRequest;
import com.example.StageTalanChat.dto.LoginResponse;
import com.example.StageTalanChat.dto.SignupRequest;
import com.example.StageTalanChat.entities.User;
import com.example.StageTalanChat.services.UserService;
import com.example.StageTalanChat.services.jwt.UserServiceImpl;
import com.example.StageTalanChat.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/login")
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final UserServiceImpl userService;
  private final UserService userService1;
  private final JwtUtil jwtUtil;
  @Autowired
  private PasswordEncoder passwordEncoder;


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
      user = userService1.getUserByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

      // Increment the user's active value
      userService1.incrementUserActive(user.getId());

    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    String jwt = jwtUtil.generateToken(userDetails.getUsername());
    LoginResponse loginResponse = new LoginResponse(jwt, user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), user.getRole());
    return ResponseEntity.ok(loginResponse);
  }

  @GetMapping("/github")
  public ResponseEntity<String> githubLogin(@RequestParam("code") String code) {
    // Remplacez par les informations spécifiques à votre application GitHub
    String clientId = "Ov23lihM5V47f1ehdypf";
    String clientSecret = "8188220b2fbbc25c70fb99d7406e2e63f1833b9b";
    String redirectUri = "http://localhost:4200/callback"; // L'URL de redirection

    // Échange du code contre un jeton d'accès
    String tokenUrl = "https://github.com/login/oauth/access_token";
    RestTemplate restTemplate = new RestTemplate();

    // Construire le corps de la requête
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("client_id", clientId);
    body.add("client_secret", clientSecret);
    body.add("code", code);
    body.add("redirect_uri", redirectUri);

    // Créer l'entête de la requête
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

    // Effectuer la requête POST pour obtenir le jeton d'accès
    ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);

    // Extraire le jeton d'accès de la réponse
    String accessToken = extractAccessToken(response.getBody());

    // Utiliser le jeton d'accès pour obtenir les informations utilisateur
    String userInfoUrl = "https://api.github.com/user";
    HttpHeaders userHeaders = new HttpHeaders();
    userHeaders.set("Authorization", "Bearer " + accessToken);
    HttpEntity<String> userRequest = new HttpEntity<>(userHeaders);

    ResponseEntity<Map> userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userRequest, Map.class);
    Map<String, Object> user = userResponse.getBody();

    // Extraire les informations utilisateur
    String email ="jawhertalbi12@gmail.com";
    String name = (String) user.get("name");
    String lastname=name;
    String login = (String) user.get("login");

    System.out.println(email);
    System.out.println(name);


    // Vérifiez si l'utilisateur existe dans votre base de données
    User existingUser = userService1.getUserByEmail(email).orElse(null);

    if (existingUser == null) {
      // L'utilisateur n'existe pas, donc nous devons le créer
      User newUser = new User();
      newUser.setNom(login);
      newUser.setPrenom(login); // GitHub ne fournit pas directement le prénom, utilisez login ou un autre attribut
      newUser.setEmail(email);
      newUser.setRole("CLIENT");
      String pass="123456jawher";
      newUser.setPassword(pass);
      newUser.setActive(0);
      existingUser = userService1.save(newUser);
    }

    // Générer un jeton JWT pour l'utilisateur
    String jwt = jwtUtil.generateToken(existingUser.getEmail());

    // Retourner le JWT et d'autres détails utilisateur si nécessaire
    return ResponseEntity.ok("User authenticated: " + existingUser.getNom() + " (" + existingUser.getEmail() + ") JWT: " + jwt);
  }

  @GetMapping("/github/auth")
  public ResponseEntity<String> redirectToGithub() {
    String clientId = "Ov23lihM5V47f1ehdypf";
    String redirectUri = "http://localhost:4200/callback"; // Votre URL de redirection

    String authorizationUrl = "https://github.com/login/oauth/authorize"
      + "?client_id=" + clientId
      + "&redirect_uri=" + redirectUri
      + "&scope=user:email"; // Ajout du scope user:email

    return ResponseEntity.status(HttpStatus.FOUND)
      .header(HttpHeaders.LOCATION, authorizationUrl)
      .build();
  }


  private String extractAccessToken(String responseBody) {
    // Extraire le jeton d'accès du corps de la réponse
    // Utilisez une bibliothèque JSON ou une méthode simple pour extraire le jeton
    String[] parts = responseBody.split("&");
    for (String part : parts) {
      String[] keyValue = part.split("=");
      if (keyValue.length == 2 && keyValue[0].equals("access_token")) {
        return keyValue[1];
      }
    }
    return null;
  }










}
