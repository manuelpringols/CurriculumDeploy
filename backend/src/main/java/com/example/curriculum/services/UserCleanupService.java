package com.example.curriculum.services;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.curriculum.entity.User;
import com.example.curriculum.repository.UserRepository;

@Service
public class UserCleanupService {

    private final UserRepository userRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(UserCleanupService.class);

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
            logger.info("Eliminati " + usersToDelete.size() + " utenti creati più di un'ora fa.", usersToDelete.size());
        } else {
            logger.info("Nessun utente da eliminare.");
        }
    }
}