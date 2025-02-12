package br.com.sabiox.sabiox_tool.util.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequestDTO(
    @NotBlank(message = "Title field is mandatory")
    String title,

    @NotBlank(message = "Description field is mandatory")
    @Size(max = 400, message = "Description cannot exceed 400 characters")
    String description,

    @NotNull(message = "User Id field is mandatory")
    Long userId
) {
}