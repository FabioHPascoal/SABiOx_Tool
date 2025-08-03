package br.com.sabiox.sabiox_tool.domain.user;

import org.springframework.web.multipart.MultipartFile;

public record RegisterRequestDTO(
        String name,
        String username,
        String email,
        String password,
        MultipartFile avatar)
{}
