package br.com.sabiox.sabiox_tool.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.sabiox.sabiox_tool.services.UserService;
import br.com.sabiox.sabiox_tool.util.dtos.request.UserRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.UserResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> readUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> readAllUsers() {
        return ResponseEntity.ok(userService.readAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserResponseDTO>> readAllActiveUsers() {
        return ResponseEntity.ok(userService.readAllAtivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.update(id, userRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}