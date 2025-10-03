package br.com.sabiox.sabiox_tool.domain.ProjectUser;

import br.com.sabiox.sabiox_tool.domain.user.UserReducedDTO;

import java.time.LocalDate;

public record ProjectUserResponseDTO(Long projectId,
                                     String title,
                                     ParticipationType role,
                                     LocalDate creationDate,
                                     UserReducedDTO owner)
{
    public ProjectUserResponseDTO(ProjectUser projectUser) {
        this(
                projectUser.getProject().getId(),
                projectUser.getProject().getTitle(),
                projectUser.getParticipationType(),
                projectUser.getProject().getCreationDate(),
                new UserReducedDTO(projectUser.getProject().getOwner())
        );
    }
}