package com.example.curriculum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.curriculum.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {

}
