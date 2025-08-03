package br.com.sabiox.sabiox_tool.domain.user;

public record IndexUsersRequestDTO(
        String name,
        String email,
        UserRole role)
{}
