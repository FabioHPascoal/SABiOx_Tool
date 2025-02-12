package br.com.sabiox.sabiox_tool.util.mappers.phases.requirements;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sabiox.sabiox_tool.model.phases.requirements.RequirementActivity;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.requirements.RequirementActivityResponseDTO;

public class RequirementActivityMapper {
    public static RequirementActivityResponseDTO toDto(RequirementActivity requirementActivity) {
        return new RequirementActivityResponseDTO(
                requirementActivity.getId(),
                requirementActivity.getPhase().getId(),
                requirementActivity.getLifeCycle().getId(),
                requirementActivity.getLifeCycleStage(),
                requirementActivity.getPurpose()
        );
    }

    public static List<RequirementActivityResponseDTO> toDtoList(List<RequirementActivity> requirementActivities) {
        if (requirementActivities == null) {
            return Collections.emptyList();
        }

        return requirementActivities.stream()
                .map(RequirementActivityMapper::toDto)
                .collect(Collectors.toList());
    }
}
