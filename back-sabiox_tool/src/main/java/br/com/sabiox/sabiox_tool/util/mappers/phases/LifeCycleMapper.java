package br.com.sabiox.sabiox_tool.util.mappers.phases;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sabiox.sabiox_tool.model.phases.LifeCycle;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.LifeCycleResponseDTO;

public class LifeCycleMapper {
    public static LifeCycleResponseDTO toDto(LifeCycle lifeCycle) {
        return new LifeCycleResponseDTO(
                lifeCycle.getId(),
                lifeCycle.getPhase().getId(),
                lifeCycle.getStartDate()
        );
    }

    public static List<LifeCycleResponseDTO> toDtoList(List<LifeCycle> lifeCycles) {
        if (lifeCycles == null) {
            return Collections.emptyList();
        }

        return lifeCycles.stream()
                .map(LifeCycleMapper::toDto)
                .collect(Collectors.toList());
    }
}