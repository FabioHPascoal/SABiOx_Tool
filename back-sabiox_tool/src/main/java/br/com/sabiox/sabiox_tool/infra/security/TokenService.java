package br.com.sabiox.sabiox_tool.infra.security;

import br.com.sabiox.sabiox_tool.domain.personal_access_token.PersonalAccessToken;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.PersonalAccessTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private PersonalAccessTokenRepository personalAccessTokenRepository;

    @Transactional
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withIssuedAt(new Date())
                    .withClaim("jti", UUID.randomUUID().toString())
                    .sign(algorithm);

            personalAccessTokenRepository.save(new PersonalAccessToken(user, token));

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token.", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    @Transactional
    public boolean revokeToken(String token) {
        try {
            PersonalAccessToken personalAccessToken = personalAccessTokenRepository.findFirstByToken(token);

            if (personalAccessToken != null) personalAccessTokenRepository.delete(personalAccessToken);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}