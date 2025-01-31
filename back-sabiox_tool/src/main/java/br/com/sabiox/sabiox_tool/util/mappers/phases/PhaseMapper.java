package br.com.sabiox.sabiox_tool.util.mappers.phases;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sabiox.sabiox_tool.model.phases.Phase;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.PhaseResponseDTO;

public class PhaseMapper {
    public static PhaseResponseDTO toDto(Phase phase) {
        return new PhaseResponseDTO(
                phase.getId(),
                phase.getProject().getId(),
                phase.getPhaseType()
        );
    }

    public static List<PhaseResponseDTO> toDtoList(List<Phase> phases) {
        if (phases == null) {
            return Collections.emptyList();
        }

        return phases.stream()
                .map(PhaseMapper::toDto)
                .collect(Collectors.toList());
    }
}