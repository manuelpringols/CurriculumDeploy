package com.example.curriculum.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor  // Costruttore con tutti i parametri
public class User implements UserDetails  {
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "email",unique = true) // L'email deve essere unica
    private String email;
	
	@Column(name = "password", nullable = false)
    private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
		
		
	}

	 private LocalDateTime createdAt;  // Data di creazione

	    @PrePersist
	    public void prePersist() {
	        this.createdAt = LocalDateTime.now();  // Imposta il timestamp di creazione
	    }


	 @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles; // Può essere una lista di stringhe, es. ["ROLE_USER", "ROLE_ADMIN"]

}
