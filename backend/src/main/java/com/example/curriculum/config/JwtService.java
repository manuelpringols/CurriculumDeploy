package com.example.curriculum.config;

import java.awt.RenderingHints.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;


@Builder
@Service
public class JwtService {/*Questa classe serve per manipolare ed estrarre informazioni dal jwtToken.
 						 In particolare ci si concentra sui claims che sono ogni proprietà del payload di un JWT(
 						 Claim	Significato	Descrizione
iss	issuer	L'emittente del JWT
sub	subject	Il soggetto del JWT. Può essere per esempio l'ID utente che ha effettuato l'accesso
aud	audience	I destinatari del JWT
exp	expiration time	La scadenza del token)*/
	private static final String SECRET_KEY = "IQ67Gq5vyhu2ZyvY4oHh5y30NXbuAAhO79V50TLNQ7He2z5nr3irsi1bfOt6VbZL6NsIWFdooXCg3xIZj1wF5XYFLhI0i2djGsgqpNVhiL7+v1XvZjE5APznRZW6XfVxa0IUmiTcSqWpHBS4dyoptTJDg3lhdJ/Kd3eNgOQRJ1Mu/TYCX3h34BWY7rpw3EUGvKzaAjztOsf595eJLssIlaQSNIFzAmWjSjwzO02i+f7hlkF0WrIhhLDKlrFdtp4u/eGlPGgrNbCJW3rxTyFs11N8D2RyKLE/x7p1Cp4R4YO+h/OlQjX8EFWAr6PPqjrWokgUL8Ix9a/zRYGTcSjdDBUeF1JMFTXSmMC7P1XV3u58oxjpPsrNfJbr1Rfn/4H2dKEqnfpDCyOyYvoX0fEXcMY4kmLucFHb53S0NWa0f1oEaOrUQzdjeyrLtn3oQyTJgxavT+IieX7oVtYnQMcawjQGWHUtgyeZsvvYFuuCNqPYhzqHiGMO/YoObi93XT/BKjXGCh9Yf5zeVRvXkRTLdEHxaTPelYYlygEj/O8LvSY="; // generata online

	public String extractUsername(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token,Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver ) { // Estrazione di un solo claim
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
		
	}
	private Claims extractAllClaims(String token) { //Estrazione di tutti i claims
		return Jwts.
				parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
				
			
		
	}
	public String generateToken(UserDetails userDetails) {//Generiamo il token utilizzando solo gli userDetails
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(//Generiamo il token
			Map<String,Object> extraClaims,
			UserDetails userDetails
			) {
			return Jwts
					.builder()
					.setClaims(extraClaims)//settiamo il claims
					.setSubject(userDetails.getUsername())//otteniamo l'username in qeusto caso l'email
					.setIssuedAt(new Date(System.currentTimeMillis()))//con questa spring capirà se il token è expired o no
					.setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 24))//con questo settiamo l'expiration date del token
					.signWith(SignatureAlgorithm.HS256,getSigningKey())//Settiamo la SigninKey e definiamo il SignatureAlgorithm
					.compact();//Crea il token vero e proprio
	}				
	
	public boolean isTokenValid(String token,UserDetails userDetails) {//Metodo per validificare un token
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	
	
	private boolean isTokenExpired(String token) {//metodo per controllare la scadenza del token
		
		// TODO Auto-generated method stub
		return extractExipraton(token).before(new Date());
	}

	private Date extractExipraton(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token,Claims::getExpiration);
	}

	private SecretKey getSigningKey() {
		 
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return  Keys.hmacShaKeyFor(keyBytes); //algoritmo che traduce la key
	}

}
