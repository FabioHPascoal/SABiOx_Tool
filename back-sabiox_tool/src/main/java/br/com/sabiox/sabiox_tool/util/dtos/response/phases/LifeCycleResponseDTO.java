package br.com.sabiox.sabiox_tool.util.dtos.response.phases;

import java.time.LocalDate;

public record LifeCycleResponseDTO(Long lifeCycleId,
                                   Long phaseId,
                                   LocalDate creationDate)
{}