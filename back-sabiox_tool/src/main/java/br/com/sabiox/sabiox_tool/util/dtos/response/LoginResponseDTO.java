package br.com.sabiox.sabiox_tool.util.dtos.response;

import br.com.sabiox.sabiox_tool.model.User;

public record LoginResponseDTO(String token, User user) {
}
