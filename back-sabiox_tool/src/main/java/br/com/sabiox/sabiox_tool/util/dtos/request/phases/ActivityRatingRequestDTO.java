package br.com.sabiox.sabiox_tool.util.dtos.request.phases;

import br.com.sabiox.sabiox_tool.model.phases.ActivityRatingType;
import jakarta.validation.constraints.NotNull;

public record ActivityRatingRequestDTO(
    @NotNull(message = "Activity rating type field is mandatory")
    ActivityRatingType activityRatingType
) {
}