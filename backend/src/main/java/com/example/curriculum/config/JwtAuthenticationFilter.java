package com.example.curriculum.config;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        // Verifica se l'Authorization header è presente e contiene "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7); // Rimuove "Bearer " dal token
        username = jwtService.extractUsername(jwtToken); // Estrai il nome utente dal token

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carica i dettagli dell'utente
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Controlla se il token è valido
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                
                // Estrai il tipo di token
                String tokenType = jwtService.extractTokenType(jwtToken);

                if ("REGISTER".equals(tokenType)) {
                    // Se il token è di tipo REGISTER, consenti il login e genera un nuovo token di tipo LOGIN
                    List<SimpleGrantedAuthority> authorities = jwtService.extractAuthorities(jwtToken);

                    // Crea un nuovo token di tipo LOGIN
                    String loginToken = jwtService.generateToken(userDetails, "LOGIN");  // Cambia con il tipo "LOGIN"

                    // Rilascia il token di login come risposta
                    response.setHeader("Authorization", "Bearer " + loginToken);
                    return;
                }

                if ("LOGIN".equals(tokenType)) {
                    // Il token è di tipo LOGIN, quindi autentica l'utente
                    List<SimpleGrantedAuthority> authorities = jwtService.extractAuthorities(jwtToken);

                    // Crea il token di autenticazione con le autorità dell'utente
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Imposta l'autenticazione nel contesto di sicurezza di Spring
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    // Token non valido per l'accesso
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }

        // Continua con la catena di filtri
        filterChain.doFilter(request, response);
    }
}
