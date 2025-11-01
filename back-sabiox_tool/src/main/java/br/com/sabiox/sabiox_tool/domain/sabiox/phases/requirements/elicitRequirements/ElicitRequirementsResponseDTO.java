package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.RequirementDTO;

import java.util.List;

public record ElicitRequirementsResponseDTO(
        Long id,
        List<RequirementDTO> requirements
) {
    public ElicitRequirementsResponseDTO(ElicitRequirements elicitRequirements) {
        this(
                elicitRequirements.getId(),
                elicitRequirements.getRequirements().stream().map(RequirementDTO::new).toList()
        );
    }
}