package br.com.sabiox.sabiox_tool.domain.sabiox.activity;

import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RequirementActivitiesResponseDTO {

    private static final Set<ActivityType> ALLOWED_TYPES =
            Set.of(ActivityType.REQ_PURP, ActivityType.REQ_DOMN, ActivityType.REQ_ELIC, ActivityType.REQ_SUBD);

    public static Map<ActivityType, ActivitySummaryDTO> mapFromLifeCycle(LifeCycle lifeCycle) {
        return lifeCycle.getActivities().entrySet().stream()
                .filter(entry -> ALLOWED_TYPES.contains(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ActivitySummaryDTO(
                                entry.getValue().getId(),
                                entry.getValue().getActivityStage()
                        )
                ));
    }
}
