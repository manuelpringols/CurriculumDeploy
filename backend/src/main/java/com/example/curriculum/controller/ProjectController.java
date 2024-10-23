package com.example.curriculum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.curriculum.services.ProjectService;
import com.example.curriculum.entity.Project;
import org.springframework.http.MediaType;


@RestController()
@RequestMapping("/api/project")
@CrossOrigin(origins = "*")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
		@GetMapping("/getName/{id}")
	public ResponseEntity<Project> getProjectName(@PathVariable Integer id){
		
		String projectName = this.projectService.getProjectName(id);
		
		return new ResponseEntity(projectName,HttpStatus.OK);
		
		
	}
	
	@GetMapping("/getDescription/{id}")
public ResponseEntity<String> getProjectDescription(@PathVariable Integer id) {
    String projectDescription = this.projectService.getProjectDescription(id);
    return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN) // Specifica che la risposta è di tipo 'text/plain'
            .body(projectDescription);
}

}
