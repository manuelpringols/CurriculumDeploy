package com.example.curriculum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	

	
	private  final JwtAuthenticationFilter jwtAuthFilter;
	
	 private static final String[] SWAGGER_WHITELIST = {
	            "/swagger-ui/**",
	            "/v3/api-docs/**",
	            "/swagger-resources/**",
	            "swagger-resources"
	    };
	
	private  final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf()
	        .disable()
	        .authorizeHttpRequests()
	        //.requestMatchers("/api/auth/register","/swagger-ui","/v3/api-docs","/swagger-resources","/swagger-resources")
	        .requestMatchers("/api/**")
	        .permitAll() // Permette l'accesso non autenticato a questo endpoint
	        .anyRequest()
	        .authenticated() // Richiede autenticazione per tutte le altre richieste
	        .and()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Utilizza JWT e non la sessione
	        .and()
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Aggiungi il filtro JWT prima del filtro di autenticazione di base

	    return http.build();
	}
}
