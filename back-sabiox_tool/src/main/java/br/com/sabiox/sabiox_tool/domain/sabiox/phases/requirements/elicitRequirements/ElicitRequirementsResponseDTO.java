package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.RequirementDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.comment.CommentDTO;

import java.util.Comparator;
import java.util.List;

public record ElicitRequirementsResponseDTO(
        Long id,
        List<RequirementDTO> requirements
) {
    public ElicitRequirementsResponseDTO(ElicitRequirements elicitRequirements, Long authUserId) {
        this(
                elicitRequirements.getId(),
                elicitRequirements.getRequirements().stream()
                        .sorted(Comparator.comparing(
                                r -> r.getRequirementType().ordinal()
                        ))
                        .map(r -> new RequirementDTO(r, authUserId))
                        .toList()
        );
    }
}
