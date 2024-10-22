package com.example.curriculum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.curriculum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepository userRepository;

	@Bean
	public UserDetailsService userDetailService() {

		return username -> userRepository.findByEmail(username);
			

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService());// Dobbiamo specificare al provider quali sono le
																// informazioni degli utenti
		authProvider.setPasswordEncoder(passwordEncoder());// Specifichiamo il password encoder
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManagaer(AuthenticationConfiguration config) throws Exception {// E il

		// responsabile
		// della
		// gestione
		// delle
		// autenticazioni,
		// ha
		// numerosi
		// metodi
		// utili
		// tipo
		// l'autenticazione
		// tramile
		// username
		// e
		// password
		return config.getAuthenticationManager();// poiche l'authenticationManager grazie a
													// l'AuthenticationConfiguration gia possiede tutte le informazioni
													// relative ai settaggi possiamo richiamare semplicemente il
													// getAuntheticationManager
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}
