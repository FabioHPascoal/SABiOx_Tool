package br.com.sabiox.sabiox_tool.domain.user;

import java.time.LocalDate;

public record UserReducedDTO(
    long id,
    String name,
    String avatarUrl
) {
    public UserReducedDTO(User user) {
            this(
                    user.getId(),
                    user.getName(),
                    user.getAvatarUrl()
            );
        }
    }
