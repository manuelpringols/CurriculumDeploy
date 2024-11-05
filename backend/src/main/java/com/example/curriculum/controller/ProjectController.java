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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "*")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @GetMapping("/getName/{id}")
    public ResponseEntity<String> getProjectName(@PathVariable Integer id) {
        try {
            String projectName = this.projectService.getProjectName(id);
            return new ResponseEntity<>(projectName, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Errore nel recupero del nome del progetto per ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDescription/{id}")
    public ResponseEntity<String> getProjectDescription(@PathVariable Integer id) {
        try {
            String projectDescription = this.projectService.getProjectDescription(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(projectDescription);
        } catch (Exception e) {
            logger.error("Errore nel recupero della descrizione del progetto per ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

