package br.com.sabiox.sabiox_tool.repositories;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long>,
        JpaSpecificationExecutor<ProjectUser> {
    ProjectUser findByEmail(String email);
}
