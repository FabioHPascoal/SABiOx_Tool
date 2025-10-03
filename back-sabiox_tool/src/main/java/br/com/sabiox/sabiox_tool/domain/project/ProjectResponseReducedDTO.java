package br.com.sabiox.sabiox_tool.domain.project;

import java.time.LocalDate;

public record ProjectResponseReducedDTO(Long projectId,
                                        Long ownerID,
                                        String title,
                                        String description,
                                        LocalDate creationDate,
                                        Boolean isEnabled)
{
    public ProjectResponseReducedDTO(Project project) {
        this(
                project.getId(),
                project.getOwner().getId(),
                project.getTitle(),
                project.getDescription(),
                project.getCreationDate(),
                project.getIsEnabled()
        );
    }
}