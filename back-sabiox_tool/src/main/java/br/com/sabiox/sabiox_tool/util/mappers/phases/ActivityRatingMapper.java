package br.com.sabiox.sabiox_tool.util.mappers.phases;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sabiox.sabiox_tool.model.phases.ActivityRating;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.ActivityRatingResponseDTO;

public class ActivityRatingMapper {
    public static ActivityRatingResponseDTO toDto(ActivityRating activityRating) {
        return new ActivityRatingResponseDTO(
                activityRating.getId(),
                activityRating.getActivityRatingType()
        );
    }

    public static List<ActivityRatingResponseDTO> toDtoList(List<ActivityRating> activityRatings) {
        if (activityRatings == null) {
            return Collections.emptyList();
        }

        return activityRatings.stream()
                .map(ActivityRatingMapper::toDto)
                .collect(Collectors.toList());
    } 
}
