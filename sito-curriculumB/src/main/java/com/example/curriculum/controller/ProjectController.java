package com.example.curriculum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curriculum.services.ProjectService;

@RestController()
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping("/getName/{id}")
	public ResponseEntity<?> getProjectName(@PathVariable Integer id){
		
		String projectName = this.projectService.getProjectName(id);
		
		return new ResponseEntity<>(projectName,HttpStatus.OK);
		
		
	}
	
	@GetMapping("/getDescription/{id}")
	public ResponseEntity<?> getProjectDescription(@PathVariable Integer id){
		
		String projectDescription = this.projectService.getProjectDescription(id);
		
		return new ResponseEntity<>(projectDescription,HttpStatus.OK);
		
		
	}

}
