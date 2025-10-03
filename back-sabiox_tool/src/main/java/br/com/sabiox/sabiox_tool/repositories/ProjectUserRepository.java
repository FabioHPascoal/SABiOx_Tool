package br.com.sabiox.sabiox_tool.repositories;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ParticipationType;
import br.com.sabiox.sabiox_tool.domain.ProjectUser.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {

    // Verifica se um usuário pertence a um projeto (membro ou outro tipo)
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);

    // Verifica se um usuário é dono do projeto
    boolean existsByProjectIdAndUserIdAndParticipationType(Long projectId, Long userId, ParticipationType type);

    // Busca o dono do projeto (caso queira carregar o User)
    Optional<ProjectUser> findByProjectIdAndParticipationType(Long projectId, ParticipationType type);

    // Lista todos os ProjectUser de um projeto
    List<ProjectUser> findByProjectId(Long projectId);

    // Lista todos os projetos de um usuário
    List<ProjectUser> findByUserId(Long userId);
}
