package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ProjectUserDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ProjectUserDTO> getAllProjects(User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return managedUser.getProjectUsers().stream().map(ProjectUserDTO::new).toList();
    }

}
