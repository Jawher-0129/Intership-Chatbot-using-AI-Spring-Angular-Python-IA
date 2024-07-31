package com.example.StageTalanChat.services;

import com.example.StageTalanChat.dto.SignupRequest;

public interface AuthService {
    boolean createUser(SignupRequest signupRequest);


}
