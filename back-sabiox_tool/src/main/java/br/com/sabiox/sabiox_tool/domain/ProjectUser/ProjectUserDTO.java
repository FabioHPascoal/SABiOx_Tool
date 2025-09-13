package br.com.sabiox.sabiox_tool.domain.ProjectUser;

import br.com.sabiox.sabiox_tool.domain.user.UserReducedDTO;

import java.time.LocalDate;
import java.util.Objects;

public record ProjectUserDTO(Long projectId,
                             String title,
                             ParticipationType role,
                             LocalDate creationDate,
                             UserReducedDTO owner)
{
    public ProjectUserDTO(ProjectUser projectUser) {
        this(
                projectUser.getProject().getId(),
                projectUser.getProject().getTitle(),
                projectUser.getParticipationType(),
                projectUser.getProject().getCreationDate(),
                new UserReducedDTO(Objects.requireNonNull(projectUser.getProject().getParticipants().stream().
                        filter(user -> user.getParticipationType() == ParticipationType.OWNER).
                        findFirst().orElse(null)).getUser())
        );
    }
}