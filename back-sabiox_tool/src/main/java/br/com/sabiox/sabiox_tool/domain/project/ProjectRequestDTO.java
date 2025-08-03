package br.com.sabiox.sabiox_tool.domain.project;

public record ProjectRequestDTO(
        String title,
        String description,
        Long userId)
{}
