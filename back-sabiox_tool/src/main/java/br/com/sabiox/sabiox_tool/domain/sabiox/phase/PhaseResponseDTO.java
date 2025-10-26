package br.com.sabiox.sabiox_tool.domain.sabiox.phase;

import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycleResponseDTO;

import java.util.List;

public record PhaseResponseDTO (
        Long phaseId,
        PhaseType phaseType,
        List<LifeCycleResponseDTO> lifeCycles
) {
    public PhaseResponseDTO(Phase phase) {
        this(
                phase.getId(),
                phase.getPhaseType(),
                phase.getLifeCycles().stream().map(LifeCycleResponseDTO::new).toList()
        );
    }
}
