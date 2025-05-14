package br.com.sabiox.sabiox_tool.repository;

import br.com.sabiox.sabiox_tool.model.PersonalAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonalAccessTokenRepository extends JpaRepository<PersonalAccessToken, UUID> {
    PersonalAccessToken findFirstByToken(String token);
}