package br.com.sabiox.sabiox_tool.domain.project;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ParticipationType;

import java.time.LocalDate;
import java.util.Objects;

public record ProjectResponseReducedDTO(Long projectId,
                                        Long ownerID,
                                        String title,
                                        String description,
                                        LocalDate creationDate,
                                        Boolean isActive)
{
    public ProjectResponseReducedDTO(Project project) {
        this(
                project.getId(),

                Objects.requireNonNull(project.getParticipants().stream().
                        filter(user -> user.getParticipationType() == ParticipationType.OWNER).
                        findFirst().orElse(null)).getUser().getId(),

                project.getTitle(),
                project.getDescription(),
                project.getCreationDate(),
                project.getIsEnabled()
        );
    }
}