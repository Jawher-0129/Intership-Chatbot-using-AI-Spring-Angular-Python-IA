package com.example.StageTalanChat.repository;

import org.springframework.data.domain.Pageable;

import com.example.StageTalanChat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

  @Query("SELECT u FROM User u ORDER BY u.active DESC")
  List<User> findTopActiveUsers(Pageable pageable);

  boolean existsByEmail(String email);
   Optional<User> findByEmail(String email);
  Optional<User> findByResetToken(String resetToken);
}
