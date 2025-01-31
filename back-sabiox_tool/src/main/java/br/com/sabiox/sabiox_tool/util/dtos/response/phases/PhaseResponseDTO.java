package br.com.sabiox.sabiox_tool.util.dtos.response.phases;

import br.com.sabiox.sabiox_tool.model.phases.PhaseType;

public record PhaseResponseDTO(Long phaseId,
                               Long projectId,
                               PhaseType phaseType)
{}