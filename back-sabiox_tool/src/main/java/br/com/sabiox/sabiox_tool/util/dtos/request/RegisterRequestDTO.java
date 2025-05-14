package br.com.sabiox.sabiox_tool.util.dtos.request;

public record RegisterRequestDTO(
        String name,
        String username,
        String email,
        String password
) {
}