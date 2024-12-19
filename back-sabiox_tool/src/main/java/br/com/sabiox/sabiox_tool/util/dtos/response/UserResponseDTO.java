package br.com.sabiox.sabiox_tool.util.dtos.response;

import java.time.LocalDateTime;

public record UserResponseDTO(Long userId,
                              String name,
                              String username,
                              String email,
                              String password,
                              LocalDateTime creationDate)
{}