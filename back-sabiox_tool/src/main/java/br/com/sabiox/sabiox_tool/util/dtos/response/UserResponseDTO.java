package br.com.sabiox.sabiox_tool.util.dtos.response;

import java.time.LocalDate;

public record UserResponseDTO(Long userId,
                              String name,
                              String username,
                              String email,
                              String password,
                              LocalDate creationDate)
{}