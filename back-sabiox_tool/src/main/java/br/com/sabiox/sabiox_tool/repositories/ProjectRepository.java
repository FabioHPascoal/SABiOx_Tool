package br.com.sabiox.sabiox_tool.repositories;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Lista todos projetos habilitados, com ordenação
    List<Project> findByIsEnabledTrue(Sort sort);

    // Busca um projeto garantindo que determinado usuário é participante
    Optional<Project> findByIdAndParticipants_UserId(Long projectId, Long userId);

    // Lista todos os projetos de um dono
    @Query("SELECT p FROM Project p JOIN p.participants pu " +
            "WHERE pu.user.id = :userId AND pu.participationType = 'OWNER'")
    List<Project> findByOwnerId(@Param("userId") Long userId);
}

