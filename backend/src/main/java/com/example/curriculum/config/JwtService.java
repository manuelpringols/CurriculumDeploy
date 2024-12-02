package com.example.curriculum.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static final String SECRET_KEY = "IQ67Gq5vyhu2ZyvY4oHh5y30NXbuAAhO79V50TLNQ7He2z5nr3irsi1bfOt6VbZL6NsIWFdooXCg3xIZj1wF5XYFLhI0i2djGsgqpNVhiL7+v1XvZjE5APznRZW6XfVxa0IUmiTcSqWpHBS4dyoptTJDg3lhdJ/Kd3eNgOQRJ1Mu/TYCX3h34BWY7rpw3EUGvKzaAjztOsf595eJLssIlaQSNIFzAmWjSjwzO02i+f7hlkF0WrIhhLDKlrFdtp4u/eGlPGgrNbCJW3rxTyFs11N8D2RyKLE/x7p1Cp4R4YO+h/OlQjX8EFWAr6PPqjrWokgUL8Ix9a/zRYGTcSjdDBUeF1JMFTXSmMC7P1XV3u58oxjpPsrNfJbr1Rfn/4H2dKEqnfpDCyOyYvoX0fEXcMY4kmLucFHb53S0NWa0f1oEaOrUQzdjeyrLtn3oQyTJgxavT+IieX7oVtYnQMcawjQGWHUtgyeZsvvYFuuCNqPYhzqHiGMO/YoObi93XT/BKjXGCh9Yf5zeVRvXkRTLdEHxaTPelYYlygEj/O8LvSY=";

    /**
     * Estrae il nome utente dal token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Metodo generico per estrarre un singolo claim dal token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Estrae tutti i claims dal token.
     */


    /**
     * Genera un token JWT con claims extra e tipo di token specifico.
     */
    public String generateToken(UserDetails userDetails, String tokenType) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("type", tokenType); // Aggiungi il tipo di token
        claims.put("authorities", userDetails.getAuthorities()); // Aggiungi autorità
        
        return createToken(claims);
    }

    /**
     * Genera un token di registrazione.
     */
    public String generateRegistrationToken(UserDetails userDetails) {
        return generateToken(userDetails, "REGISTER");
    }

    /**
     * Genera un token di login.
     */
    public String generateLoginToken(UserDetails userDetails) {
        return generateToken(userDetails, "LOGIN");
    }

    /**
     * Verifica se un token è valido.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica se il token è scaduto.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Estrae la data di scadenza del token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Estrae il tipo di token.
     */
    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get("type", String.class));
    }

    /**
     * Crea il token JWT con i claims specificati.
     */
    private String createToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Valido 1 ora
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }

    /**
     * Restituisce la chiave di firma per i token JWT.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Metodo che restituisce la chiave segreta per firmare i token
                .build()
                .parseClaimsJws(token)
                .getBody();
 }
    
    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        // Supponiamo che i ruoli siano memorizzati in un campo "roles" nel token
        List<String> roles = claims.get("roles", List.class);
        if (roles == null) {
            return new ArrayList<>();
        }
        return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }
}
