package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.model.User;
import br.com.sabiox.sabiox_tool.model.UserRole;
import br.com.sabiox.sabiox_tool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Transactional
    public User createUser(User newUser) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());

        newUser.setRole((UserRole.USER));
        newUser.setPassword(encryptedPassword);

        return this.userRepository.save(newUser);
    }
}