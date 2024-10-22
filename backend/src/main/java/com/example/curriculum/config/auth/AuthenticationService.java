package com.example.curriculum.config.auth;



import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<String> register(User userR) {// Questo metodo register ci permetterà di creare e
						
		
		// salvare uno user nel nostro database e
		var user = User.builder()				
				.email(userR.getEmail())
				.password(passwordEncoder.encode(userR.getPassword()))
				.build();
				repository.save(user);
				var jwtToken = jwtService.generateToken(user);
				// ritornare da esso, il generatedToken
				List tokenId = new ArrayList<>() ;
				
				tokenId.add(jwtToken);
				
		return tokenId;
				

	}
	
	
	

	public List<String> authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()
				) 
				);// l'autenticationManager ha bisogno di username e password per funzionare 
				
			var user = repository.findByEmail(request.getEmail());
		
		
		 var jwtToken = jwtService.generateToken(user);
		// ritornare da esso, il generatedToken
		 
		 List tokenId = new ArrayList<>() ;
		 tokenId.add(jwtToken);
		 
			return tokenId;


	}

}
