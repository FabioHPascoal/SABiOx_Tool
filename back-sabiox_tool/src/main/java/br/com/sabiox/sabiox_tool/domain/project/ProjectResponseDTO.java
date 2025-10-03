package br.com.sabiox.sabiox_tool.domain.project;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ParticipationType;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record ProjectResponseDTO(Long projectId,
                                 Long ownerId,
                                 String title,
                                 String description,
                                 LocalDate creationDate,
                                 Boolean isEnabled,
                                 List<PhaseDTO> phases)
{
    public ProjectResponseDTO(Project project) {
        this(
                project.getId(),
                project.getOwner().getId(),
                project.getTitle(),
                project.getDescription(),
                project.getCreationDate(),
                project.getIsEnabled(),
                project.getPhases().stream().map(PhaseDTO::new).toList()
        );
    }
}