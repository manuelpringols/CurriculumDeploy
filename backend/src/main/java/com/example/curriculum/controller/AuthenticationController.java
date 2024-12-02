package com.example.curriculum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curriculum.config.auth.AuthenticationRequest;
import com.example.curriculum.config.auth.AuthenticationService;
import com.example.curriculum.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Metodo per la registrazione di un nuovo utente.
     * @param request Oggetto contenente i dati per la registrazione (nome, email, password, ecc.)
     * @return Risposta con il messaggio di successo o errore
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        try {
            // Chiamata al servizio di registrazione
            String registrationResponse = authenticationService.register(request);
            // Restituisci una risposta di successo
            return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            // Gestione degli errori durante la registrazione
            return new ResponseEntity<>("Registrazione fallita: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Metodo per l'autenticazione dell'utente.
     * @param request Oggetto contenente le credenziali (email, password)
     * @return Risposta con il token JWT o messaggio di errore
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            // Chiamata al servizio di autenticazione per ottenere il token
            String token = authenticationService.authenticate(request);
            // Restituisci una risposta con il token JWT
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            // Gestione degli errori durante l'autenticazione (ad esempio credenziali non valide)
            return new ResponseEntity<>("Autenticazione fallita: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
