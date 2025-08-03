package br.com.sabiox.sabiox_tool.repositories;

import br.com.sabiox.sabiox_tool.domain.personal_access_token.PersonalAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonalAccessTokenRepository extends JpaRepository<PersonalAccessToken, UUID> {
    PersonalAccessToken findFirstByToken(String token);
}