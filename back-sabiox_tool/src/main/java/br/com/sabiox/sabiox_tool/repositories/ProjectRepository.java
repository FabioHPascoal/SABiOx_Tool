package br.com.sabiox.sabiox_tool.repositories;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByIsEnabledTrue(Sort sort);
}
