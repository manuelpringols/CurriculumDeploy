package com.example.curriculum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "progetti")
public class Project {
	
	
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer projectId;
	
 private String projectName;
 
 @Column(length = 2000)
 private String projectDescription;
 

 
 
 
 

}
