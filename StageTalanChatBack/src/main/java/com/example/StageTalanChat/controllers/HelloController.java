package com.example.StageTalanChat.controllers;

import com.example.StageTalanChat.dto.HelloResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class HelloController {

@GetMapping("/hello")
  public HelloResponse hello()
  {
    return new HelloResponse("Hello from authorized API request");
  }




}
