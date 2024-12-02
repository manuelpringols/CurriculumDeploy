package com.example.curriculum.config.auth;



import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.curriculum.config.JwtService;
import com.example.curriculum.entity.User;
import com.example.curriculum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	@Autowired
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public String register(User userR) {
	    var user = User.builder()
	            .email(userR.getEmail())
	            .password(passwordEncoder.encode(userR.getPassword()))
	            .build();
	    repository.save(user);

	    // Generazione del token di registrazione
	    var jwtToken = jwtService.generateToken(userR, "REGISTER");

	    return jwtToken;  // Restituisci il token direttamente
	}

	
	
	

	public String authenticate(AuthenticationRequest request) {
	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    request.getEmail(),
	                    request.getPassword()
	            )
	    );

	    var user = repository.findByEmail(request.getEmail());

	    Map<String, Object> claims = new HashMap<>();
	    claims.put("authorities", user.getRoles());

	    var jwtToken = jwtService.generateToken(user, "LOGIN");

	    return jwtToken;  // Restituisci il token direttamente
	}
}
