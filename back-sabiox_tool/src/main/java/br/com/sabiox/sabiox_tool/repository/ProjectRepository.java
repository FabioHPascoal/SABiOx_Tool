package br.com.sabiox.sabiox_tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sabiox.sabiox_tool.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {}