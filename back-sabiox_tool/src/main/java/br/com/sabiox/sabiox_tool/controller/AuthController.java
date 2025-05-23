package br.com.sabiox.sabiox_tool.controller;

import br.com.sabiox.sabiox_tool.model.User;
import br.com.sabiox.sabiox_tool.services.AuthService;
import br.com.sabiox.sabiox_tool.services.TokenService;
import br.com.sabiox.sabiox_tool.util.dtos.request.LoginRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.request.RegisterRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.LoginResponseDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.RegisterResponseDTO;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        var user = (User) auth.getPrincipal();

        return ResponseEntity.ok(new LoginResponseDTO(token, user));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) return ResponseEntity.badRequest().build();

        String token = authorizationHeader.replace("Bearer ", "");

        boolean ok = tokenService.revokeToken(token);

        return ok ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO body) {
        if (this.authService.findByEmail(body.email()) != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "This email is already being used."));
        }

        User newUser = new User();

        newUser.setName(body.name());
        newUser.setUsername(body.username());
        newUser.setEmail(body.email());
        newUser.setPassword(body.password());

        newUser = this.authService.createUser(newUser);

        var token = tokenService.generateToken(newUser);

        return ResponseEntity.ok(new RegisterResponseDTO(
                newUser.getId(),
                newUser.getName(),
                newUser.getEmail(),
                token
        ));
    }
}