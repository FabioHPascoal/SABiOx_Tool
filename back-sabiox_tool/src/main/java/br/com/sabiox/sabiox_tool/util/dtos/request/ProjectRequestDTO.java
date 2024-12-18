package br.com.sabiox.sabiox_tool.util.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequestDTO(
    @NotBlank(message = "Título é obrigatório")
    String title,

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200, message = "Descrição não pode exceder 200 caracteres")
    String description
) {
}