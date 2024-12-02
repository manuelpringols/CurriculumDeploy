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
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        System.out.println("Incoming request: " + request.getRequestURI());

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization header is missing or invalid.");
            try {
                filterChain.doFilter(request, response);
            } catch (java.io.IOException | ServletException e) {
                e.printStackTrace();
            }
            return;
        }

        jwtToken = authHeader.substring(7);
        System.out.println("JWT Token extracted: " + jwtToken);

        username = jwtService.extractUsername(jwtToken);
        System.out.println("Username extracted: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("User details loaded for username: " + username);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                String tokenType = jwtService.extractTokenType(jwtToken);
                System.out.println("Token type extracted: " + tokenType);

                if (request.getRequestURI().equals("/api/auth/authenticate")) {
                    System.out.println("Request is for /api/auth/authenticate endpoint.");
                    if (!"REGISTER".equals(tokenType)) {
                        System.out.println("Token type is not REGISTER. Access denied.");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                    System.out.println("Token type is REGISTER. Access granted.");
                } else {
                    System.out.println("Request is for another endpoint.");
                    if (!"LOGIN".equals(tokenType)) {
                        System.out.println("Token type is not LOGIN. Access denied.");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                    System.out.println("Token type is LOGIN. Access granted.");
                }

                // Autenticazione standard per entrambi i casi
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authentication set in SecurityContext.");
            } else {
                System.out.println("Token is invalid.");
            }
        } else {
            System.out.println("Username is null or user is already authenticated.");
        }

        try {
            filterChain.doFilter(request, response);
        } catch (java.io.IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
