package br.com.sabiox.sabiox_tool.controller;

import br.com.sabiox.sabiox_tool.domain.user.IndexUsersRequestDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.domain.user.UserDTO;
import br.com.sabiox.sabiox_tool.domain.user.UserRole;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import br.com.sabiox.sabiox_tool.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal User authUser) {
        return ResponseEntity.ok(new UserDTO(authUser));
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(IndexUsersRequestDTO filterDTO) {
        List<UserDTO> users = this.authService.getAllUsers(filterDTO);

        return ResponseEntity.ok(users);
    }

    @PutMapping("/admin/user/{userId}/update-role")
    public ResponseEntity<?> updateUserRole(
            @PathVariable Long userId,
            @RequestBody String newRole,
            @AuthenticationPrincipal User authUser
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (user == null) return ResponseEntity.badRequest().build();
        if (user.equals(authUser))
            return ResponseEntity.badRequest().body("You can't change your own role.");

        user.setRole(UserRole.valueOf(newRole));
        userRepository.save(user);

        return ResponseEntity.ok(new UserDTO(user));
    }

    @PostMapping("/user/avatar")
    public ResponseEntity<String> uploadAvatar(
            @RequestParam("avatar") MultipartFile avatar,
            @AuthenticationPrincipal User authUser
    ) {
        String avatarUrl = this.authService.saveAvatar(authUser.getId(), avatar);

        return ResponseEntity.ok(avatarUrl);
    }

    @DeleteMapping("/user/avatar")
    public ResponseEntity<Void> uploadAvatar(@AuthenticationPrincipal User authUser) {
        this.authService.deleteAvatar(authUser.getId());

        return ResponseEntity.ok().build();
    }
}
