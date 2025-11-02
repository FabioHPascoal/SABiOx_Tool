package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.CommentDTO;
import br.com.sabiox.sabiox_tool.domain.user.UserReducedDTO;

import java.time.LocalDate;
import java.util.List;

public record RequirementDTO(
        Long id,
        RequirementType requirementType,
        String description,
        LocalDate creationDate,
        UserReducedDTO user,
        List<CommentDTO> comments
) {
    public RequirementDTO(Requirement requirement, Long authUserId) {
        this(
                requirement.getId(),
                requirement.getRequirementType(),
                requirement.getDescription(),
                requirement.getCreationDate(),
                new UserReducedDTO(requirement.getUser()),
                requirement.getComments()
                        .stream()
                        .map(c -> new CommentDTO(c, authUserId))
                        .toList()
        );
    }
}
