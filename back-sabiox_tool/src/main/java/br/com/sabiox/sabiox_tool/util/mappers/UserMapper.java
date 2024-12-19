package br.com.sabiox.sabiox_tool.util.mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sabiox.sabiox_tool.model.User;
import br.com.sabiox.sabiox_tool.util.dtos.response.UserResponseDTO;

public class UserMapper {
    public static UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getCreationDate()
        );
    }

    public static List<UserResponseDTO> toDtoList(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}