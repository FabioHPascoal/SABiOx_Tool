package br.com.sabiox.sabiox_tool.util.dtos.response.phases;

import br.com.sabiox.sabiox_tool.model.phases.ActivityRatingType;

public record ActivityRatingResponseDTO(Long activityRatingId,
                                        ActivityRatingType activityRatingType)
{}