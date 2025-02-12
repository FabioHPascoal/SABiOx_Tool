package br.com.sabiox.sabiox_tool.util.dtos.response.phases.requirements;

import br.com.sabiox.sabiox_tool.model.phases.LifeCycleStage;

public record RequirementActivityResponseDTO (Long requirementActivityId,
                                              Long phaseId,
                                              Long lifeCycleId,
                                              LifeCycleStage lifeCycleStage,
                                              String purpose)
{}