package com.example.curriculum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SitoCurriculumApplication {

	public static void main(String[] args) {
		SpringApplication.run(SitoCurriculumApplication.class, args);
	}

}
