package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.CommentDTO;

import java.time.LocalDate;
import java.util.List;

public record RequirementResponseDTO(
        Long id,
        Long activityId,
        Long userId,
        RequirementType requirementType,
        String description,
        LocalDate creationDate,
        List<CommentDTO> comments
) {
    public RequirementResponseDTO(Requirement requirement) {
        this(
                requirement.getId(),
                requirement.getElicitRequirements().getId(),
                requirement.getUser().getId(),
                requirement.getRequirementType(),
                requirement.getDescription(),
                requirement.getCreationDate(),
                requirement.getComments().stream().map(CommentDTO::new).toList()
        );
    }
}