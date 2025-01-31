package br.com.sabiox.sabiox_tool.util.dtos.request.phases;

import br.com.sabiox.sabiox_tool.model.phases.PhaseType;
import jakarta.validation.constraints.NotNull;

public record PhaseRequestDTO(
    @NotNull(message = "Project ID field is mandatory")
    Long projectId,

    @NotNull(message = "Phase type field is mandatory")
    PhaseType phaseType
) {
}