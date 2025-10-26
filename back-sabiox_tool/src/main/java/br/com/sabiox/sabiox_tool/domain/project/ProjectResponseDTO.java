package br.com.sabiox.sabiox_tool.domain.project;

import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseReducedDTO;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public record ProjectResponseDTO(Long projectId,
                                 Long ownerId,
                                 String title,
                                 String description,
                                 LocalDate creationDate,
                                 Boolean isEnabled,
                                 List<PhaseReducedDTO> phases
) {
    public ProjectResponseDTO(Project project) {
        this(
                project.getId(),
                project.getOwner().getId(),
                project.getTitle(),
                project.getDescription(),
                project.getCreationDate(),
                project.getIsEnabled(),
                project.getPhases()
                        .values().stream()
                        .sorted(Comparator.comparing(Phase::getId))
                        .map(PhaseReducedDTO::new)
                        .toList()
        );
    }
}