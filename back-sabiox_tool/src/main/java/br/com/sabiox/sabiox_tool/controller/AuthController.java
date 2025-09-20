package br.com.sabiox.sabiox_tool.controller;

import br.com.sabiox.sabiox_tool.domain.user.*;
import br.com.sabiox.sabiox_tool.infra.security.TokenService;
import br.com.sabiox.sabiox_tool.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<br.com.sabiox.sabiox_tool.domain.user.LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        var user = (User) auth.getPrincipal();

        return ResponseEntity.ok(new LoginResponseDTO(token, new UserDTO(user)));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) return ResponseEntity.badRequest().build();

        String token = authorizationHeader.replace("Bearer ", "");

        boolean ok = tokenService.revokeToken(token);

        return ok ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@ModelAttribute @Valid RegisterRequestDTO body) {
        if (this.authService.findByEmail(body.email()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "This email is already being used."));
        }

        User newUser = new User();

        newUser.setName(body.name());
        newUser.setUsername(body.username());
        newUser.setEmail(body.email());
        newUser.setPassword(body.password());

        newUser = this.authService.createUser(newUser);

        if (body.avatar() != null) {
            newUser.setAvatarUrl(this.authService.saveAvatar(newUser.getId(), body.avatar()));
        }

        var token = tokenService.generateToken(newUser);

        return ResponseEntity.ok(new RegisterResponseDTO(
                newUser.getId(),
                newUser.getName(),
                newUser.getEmail(),
                newUser.getRole(),
                newUser.getAvatarUrl(),
                token
        ));
    }
}
