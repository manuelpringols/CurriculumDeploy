package com.example.curriculum.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserDetailsService userDetailsService;// implementazione dell'interfaccia UserDetails con l'uso dell
														// prefisso final per indicare a spring che vogliamo prendere
														// gli user direttamente dal nostro database

	@Override
	protected void doFilterInternal( // la prima cosa che deve fare il filtro è controllare se la richiesta ha il jwt
										// token
			@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		final String jwt; // token
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);// numero di caratteri della stringa Bearer
		final String userEmail;
		userEmail = jwtService.extractUsername(jwt); // creiamo una classe in grado di manipolare il jwt token

		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {// quando il secondo
																									// parametro è null,
																									// vuol dire che
																									// l'user non è
																									// ancora
																									// autenticato
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

			if (jwtService.isTokenValid(jwt,
					userDetails)) { /*
									 * Il JwtService ci permette di leggere all interno del token email e
									 * password, in modo da controllare se tale utente è gia presente nel nostro
									 * archivio, database
									 */

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);

				filterChain.doFilter(request, response);
			}

		}
	}

}
