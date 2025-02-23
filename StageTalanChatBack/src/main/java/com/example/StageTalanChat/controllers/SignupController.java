package com.example.StageTalanChat.controllers;
import com.example.StageTalanChat.dto.SignupRequest;
import com.example.StageTalanChat.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@CrossOrigin(origins ="http://localhost:4200")
@AllArgsConstructor
public class SignupController {

    private final AuthService authService;
    @PostMapping("/p1")
    public ResponseEntity<String> signupUser(@RequestBody SignupRequest signupRequest)
    {
        boolean isUserCreated=authService.createUser(signupRequest);

        if(isUserCreated)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfuly");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create a user");
        }
    }
}



