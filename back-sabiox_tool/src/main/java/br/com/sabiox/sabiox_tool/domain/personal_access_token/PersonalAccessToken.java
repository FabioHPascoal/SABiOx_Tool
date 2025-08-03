package br.com.sabiox.sabiox_tool.domain.personal_access_token;

import br.com.sabiox.sabiox_tool.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table(name = "personal_access_tokens")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalAccessToken {
    @Id
    @GeneratedValue
    private UUID id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PersonalAccessToken(User user, String token) {
        this.setUser(user);
        this.setToken(token);
    }
}
