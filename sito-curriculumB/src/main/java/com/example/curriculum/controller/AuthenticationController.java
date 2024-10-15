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
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User request) {
		// Il RegisterRequest avra il compito di accumulare tutte le richiste o le
		// informazioni tipo nome, cognome etc
		
	
		return new ResponseEntity<>(authenticationService.register(request),HttpStatus.OK);
		
		
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
		// Il RegisterRequest avra il compito di accumulare tutte le richiste o le
		// informazioni tipo nome, cognome etc
	
		return new ResponseEntity<>(authenticationService.authenticate(request),HttpStatus.OK);
		
		
	}
	
	
	
}
