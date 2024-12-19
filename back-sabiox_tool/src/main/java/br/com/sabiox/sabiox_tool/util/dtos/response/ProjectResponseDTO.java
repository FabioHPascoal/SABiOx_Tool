package br.com.sabiox.sabiox_tool.util.dtos.response;

public record ProjectResponseDTO(Long projectId,
                                 Long userId,
                                 String title,
                                 String description) 
{}