package br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivitiesDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.RequirementActivitiesResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseType;

import java.time.LocalDate;

public record LifeCycleResponseDTO(
        Long id,
        PhaseType phaseType,
        LocalDate startDate,
        ActivitiesDTO activitiesDTO
) {
    public LifeCycleResponseDTO(LifeCycle lifeCycle) {
        this(
                lifeCycle.getId(),
                lifeCycle.getPhase().getPhaseType(),
                lifeCycle.getStartDate(),
                switch(lifeCycle.getPhase().getPhaseType()) {
                    case REQUIREMENTS -> new RequirementActivitiesResponseDTO(lifeCycle);
                    case SETUP, CAPTURE, DESIGN, IMPLEMENTATION -> null;
                }
        );
    }
}
