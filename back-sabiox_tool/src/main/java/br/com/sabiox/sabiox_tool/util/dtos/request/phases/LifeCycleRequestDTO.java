package br.com.sabiox.sabiox_tool.util.dtos.request.phases;

import jakarta.validation.constraints.NotNull;

public record LifeCycleRequestDTO(
    @NotNull(message = "Phase Id field is mandatory")
    Long phaseId
) {
}