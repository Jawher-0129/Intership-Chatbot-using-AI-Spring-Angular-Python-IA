package com.example.StageTalanChat.services.jwt;

import com.example.StageTalanChat.entities.User;
import com.example.StageTalanChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user=userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found with email:"+email));
    return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),Collections.emptyList());
  }
}
