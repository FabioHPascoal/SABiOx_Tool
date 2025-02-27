package br.com.sabiox.sabiox_tool.util.dtos.response;

import java.time.LocalDate;

public record ProjectResponseDTO(Long projectId,
                                 Long userId,
                                 String title,
                                 String description,
                                 LocalDate creationDate,
                                 Boolean isActive)
{}