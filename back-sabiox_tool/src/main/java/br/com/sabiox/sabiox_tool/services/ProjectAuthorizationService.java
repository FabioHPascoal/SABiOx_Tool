package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ParticipationType;
import br.com.sabiox.sabiox_tool.repositories.ProjectUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProjectAuthorizationService {

    private final ProjectUserRepository projectUserRepository;

    public void assertOwner(Long projectId, Long userId) {
        boolean isOwner = projectUserRepository.existsByProjectIdAndUserIdAndParticipationType(
                projectId, userId, ParticipationType.OWNER);

        if (!isOwner) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You're not the owner of this project.");
        }
    }

    public void assertMember(Long projectId, Long userId) {
        boolean isMember = projectUserRepository.existsByProjectIdAndUserId(projectId, userId);

        if (!isMember) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not a member of this project.");
        }
    }

    public void assertNotMember(Long projectId, Long userId) {
        boolean isMember = projectUserRepository.existsByProjectIdAndUserId(projectId, userId);

        if (isMember) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You're already a member of this project.");
        }
    }
}
