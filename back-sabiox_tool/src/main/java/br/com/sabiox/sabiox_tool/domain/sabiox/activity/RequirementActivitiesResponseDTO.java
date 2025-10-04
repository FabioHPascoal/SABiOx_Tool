package br.com.sabiox.sabiox_tool.domain.sabiox.activity;

import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseType;

import java.util.Map;
import java.util.stream.Collectors;

public record RequirementActivitiesResponseDTO(Map<ActivityType, Long> activities) implements ActivitiesDTO {
    public RequirementActivitiesResponseDTO(LifeCycle lifeCycle) {
        this(lifeCycle.getActivities().entrySet().stream()
                        .filter(entry -> entry.getValue()
                                .getLifeCycle().getPhase().getPhaseType() == PhaseType.REQUIREMENTS)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().getId()
                        ))
        );
    }
}
