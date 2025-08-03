package br.com.sabiox.sabiox_tool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sabiox.sabiox_tool.domain.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {}