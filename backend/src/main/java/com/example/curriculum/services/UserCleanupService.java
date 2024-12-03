package com.example.curriculum.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.curriculum.entity.User;
import com.example.curriculum.repository.UserRepository;

@Service
public class UserCleanupService {

    private final UserRepository userRepository;

    public UserCleanupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Task schedulato che viene eseguito ogni 10 minuti
    @Scheduled(fixedRate = 600000) // Ogni 10 minuti (600000 millisecondi)
    public void deleteUsersCreatedMoreThanOneHourAgo() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<User> usersToDelete = userRepository.findByCreatedAtBefore(oneHourAgo);

        if (!usersToDelete.isEmpty()) {
            userRepository.deleteAll(usersToDelete);
            System.out.println("Eliminati " + usersToDelete.size() + " utenti creati più di un'ora fa.");
        }
    }
}