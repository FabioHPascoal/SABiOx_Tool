package br.com.sabiox.sabiox_tool.util.dtos.request.phases.requirements;

import br.com.sabiox.sabiox_tool.model.phases.LifeCycleStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequirementActivityRequestDTO(

    @NotNull(message = "Life cycle Id field is mandatory")
    Long lifeCycleId,

    @NotNull(message = "Phase Id field is mandatory")
    Long phaseId,

    @NotBlank(message = "Purpose field is mandatory")
    @Size(max = 400, message = "Purpose cannot exceed 400 characters")
    String purpose,

    @NotNull(message = "Phase type field is mandatory")
    LifeCycleStage lifeCycleStage

) {
}