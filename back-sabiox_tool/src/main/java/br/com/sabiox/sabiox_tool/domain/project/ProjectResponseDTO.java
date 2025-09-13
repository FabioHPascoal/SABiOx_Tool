package br.com.sabiox.sabiox_tool.domain.project;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ParticipationType;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseDTO;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record ProjectResponseDTO(Long projectId,
                                 Long ownerId,
                                 String title,
                                 String description,
                                 LocalDate creationDate,
                                 Boolean isActive,
                                 List<PhaseDTO> phases)
{
    public ProjectResponseDTO(Project project) {
        this(
                project.getId(),

                Objects.requireNonNull(project.getParticipants().stream().
                        filter(user -> user.getParticipationType() == ParticipationType.OWNER).
                        findFirst().orElse(null)).getUser().getId(),

                project.getTitle(),
                project.getDescription(),
                project.getCreationDate(),
                project.getIsEnabled(),
                project.getPhases().stream().map(PhaseDTO::new).toList()
        );
    }
}