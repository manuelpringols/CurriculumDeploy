package com.example.curriculum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.home") + "/uploads";
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + UPLOAD_DIRECTORY + "/");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // consente tutte le richieste a tutti gli endpoint
                .allowedOrigins("*") // sostituisci con il tuo dominio GitHub Pages
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // specifica i metodi consentiti
                .allowCredentials(false); // se hai bisogno di inviare credenziali
    }
}

