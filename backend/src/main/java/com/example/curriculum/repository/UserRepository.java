package com.example.curriculum.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.curriculum.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	
	User findByEmail(String email);
	
    List<User> findByCreatedAtBefore(LocalDateTime time);



}
