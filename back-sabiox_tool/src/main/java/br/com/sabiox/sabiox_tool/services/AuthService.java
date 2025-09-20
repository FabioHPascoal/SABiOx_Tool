package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.domain.user.IndexUsersRequestDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.domain.user.UserDTO;
import br.com.sabiox.sabiox_tool.domain.user.UserRole;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import br.com.sabiox.sabiox_tool.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${app.backend.url}")
    private String serverUrl;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Transactional
    public User createUser(User newUser) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());

        newUser.setRole((UserRole.USER));
        newUser.setPassword(encryptedPassword);

        return this.userRepository.save(newUser);
    }

    public List<UserDTO> getAllUsers(IndexUsersRequestDTO filterDTO) {
        Specification<User> spec = Specification
                .where(UserSpecification.hasName(filterDTO.name()))
                .and(UserSpecification.hasEmail(filterDTO.email()))
                .and(UserSpecification.hasRole(filterDTO.role()));

        List<User> allUsers = this.userRepository.findAll(
                spec,
                Sort.by(Sort.Direction.ASC, "name")
        );

        return allUsers
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    @Transactional
    public String saveAvatar(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        try {
            Path directory = Paths.get(uploadDir);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String fileName = "avatar_" + userId + "_" + System.currentTimeMillis() + ".png";
            Path filePath = directory.resolve(fileName);

            Files.write(filePath, file.getBytes());

            if (user.getAvatarUrl() != null) {
                deleteAvatar(userId);
            }

            String avatarUrl = serverUrl + "/" + uploadDir + "/" + fileName;

            user.setAvatarUrl(avatarUrl);
            userRepository.save(user);

            return avatarUrl;
        } catch (IOException e) {
            throw new RuntimeException("Error trying to save image", e);
        }
    }

    @Transactional
    public void deleteAvatar(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (user.getAvatarUrl() == null) return;

        try {
            String fileName = user.getAvatarUrl().substring(user.getAvatarUrl().lastIndexOf("/") + 1);

            Path filePath = Paths.get(uploadDir, fileName);

            Files.deleteIfExists(filePath);

            user.setAvatarUrl(null);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Erro trying to delete the avatar.", e);
        }
    }
}