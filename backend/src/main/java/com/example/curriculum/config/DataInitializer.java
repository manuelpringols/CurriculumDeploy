package com.example.curriculum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.curriculum.entity.Project;
import com.example.curriculum.entity.User;
import com.example.curriculum.repository.ProjectRepository;
import com.example.curriculum.repository.UserRepository;


@Component
public class DataInitializer {
	
	
	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private ProjectRepository projectRepository;
/*
	    @Override
	    public void run(String... args) throws Exception {
	        // Verifica se l'utente esiste già
	        if (userRepository.count() == 0) {
	            User user = new User();
	            user.setEmail("manuelpringols@gmail.com");
	            user.setPassword("password"); // Assicurati di criptare la password in produzione
	            userRepository.save(user);

	            // Aggiungi i progetti
	            
	        }
	        
        if (projectRepository.count() == 0 ) {
	        
	        Project project1 = new Project();
            project1.setProjectName("PinGo");
            project1.setProjectDescription("PinGo è un'app per dispositivi mobili a cui sto lavorando da circa un anno con alcuni amici. Il progetto è realizzato utilizzando Ionic, un framework che trasforma il codice Angular e HTML in linguaggio nativo (Java/Kotlin) per Android Studio. L'obiettivo di PinGo è fornire una soluzione di geolocalizzazione che consenta agli utenti di creare, condividere e visualizzare punti d'interesse sulla mappa, creando una community attiva e partecipativa. ");
            projectRepository.save(project1);
             // Forza il salvataggio

            Project project2 = new Project();
            project2.setProjectName("CRM per PinGo");
            project2.setProjectDescription(" Questo è un progetto interno, nato dall’esigenza di gestire meglio le attività e le task del team che lavora su PinGo. Ho realizzato una sorta di CRM utilizzando tecnologie di front-end e back-end per monitorare le attività in corso, le task completate e quelle da assegnare. Questo strumento ci permette di mantenere una visione chiara sullo stato dei lavori e migliorare la produttività. ");
            projectRepository.save(project2);
             // Forza il salvataggio
	    }
	    }
	    
	    */

}
