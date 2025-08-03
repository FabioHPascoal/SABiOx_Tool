package br.com.sabiox.sabiox_tool.domain.project;

import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseDTO;

import java.time.LocalDate;
import java.util.List;

public record ProjectResponseDTO(Long projectId,
                                 Long userId,
                                 String title,
                                 String description,
                                 LocalDate creationDate,
                                 Boolean isActive,
                                 List<PhaseDTO> phases)
{
    public ProjectResponseDTO(Project project) {
        this(
                project.getId(),
                project.getUser().getId(),
                project.getTitle(),
                project.getDescription(),
                project.getCreationDate(),
                project.getIsEnabled(),
                project.getPhases().stream().map(PhaseDTO::new).toList()
        );
    }
}