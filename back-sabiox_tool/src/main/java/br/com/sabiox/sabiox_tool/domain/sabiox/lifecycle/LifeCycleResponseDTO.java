package br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivitySummaryDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.RequirementActivitiesResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseType;

import java.time.LocalDate;
import java.util.Map;

public record LifeCycleResponseDTO(
        Long id,
        PhaseType phaseType,
        LocalDate startDate,
        Map<ActivityType, ActivitySummaryDTO> activities
) {
    public LifeCycleResponseDTO(LifeCycle lifeCycle) {
        this(
                lifeCycle.getId(),
                lifeCycle.getPhase().getPhaseType(),
                lifeCycle.getStartDate(),
                switch (lifeCycle.getPhase().getPhaseType()) {
                    case REQUIREMENTS -> RequirementActivitiesResponseDTO.mapFromLifeCycle(lifeCycle);
                    case SETUP, CAPTURE, DESIGN, IMPLEMENTATION -> Map.of();
                }
        );
    }
}

