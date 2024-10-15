package com.example.curriculum.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.curriculum.entity.Project;
import com.example.curriculum.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	public String getProjectName(Integer id){
		Optional<Project> progetto =  projectRepository.findById(id);
		
		return progetto.get().getProjectName();
	}
	
	public String getProjectDescription(Integer id){
		Optional<Project> progetto =  projectRepository.findById(id);
		
		return progetto.get().getProjectDescription();
	}

}
