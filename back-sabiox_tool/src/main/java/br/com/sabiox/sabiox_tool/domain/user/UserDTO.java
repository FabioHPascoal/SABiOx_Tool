package br.com.sabiox.sabiox_tool.domain.user;


import java.time.LocalDate;

public record UserDTO(
        long id,
        String name,
        String username,
        String email,
        UserRole role,
        LocalDate creationDate,
        String avatarUrl
) {
    public UserDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreationDate(),
                user.getAvatarUrl()
        );
    }
}
