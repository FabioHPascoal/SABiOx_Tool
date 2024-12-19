package br.com.sabiox.sabiox_tool.util.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
    
    @NotBlank(message = "Name field is mandatory")
    String name,

    @NotBlank(message = "Username field is mandatory")
    String username,

    @NotBlank(message = "Email field is mandatory")
    String email,

    @NotBlank(message = "Password field is mandatory")
    String password

) {
}