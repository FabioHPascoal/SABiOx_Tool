package br.com.sabiox.sabiox_tool.domain.user;

public record RegisterResponseDTO(
        Long id,
        String name,
        String email,
        UserRole role,
        String avatarUrl,
        String token)
{}
