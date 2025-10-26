package br.com.sabiox.sabiox_tool.services.sabiox;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseReducedDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.ProjectRepository;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import br.com.sabiox.sabiox_tool.services.ProjectAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class PhaseService {
    @Autowired
    private ProjectAuthorizationService projectAuthorizationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public List<PhaseResponseDTO> getAllPhases(
            Long userId,
            Long projectId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        projectAuthorizationService.assertMember(projectId, userId);

        List<Phase> phases = project.getPhases().values().stream().toList();
        return phases.stream().sorted(Comparator.comparing(Phase::getId)).map(PhaseResponseDTO::new).toList();
    }
}