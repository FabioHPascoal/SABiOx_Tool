package br.com.sabiox.sabiox_tool.services.sabiox;

import static br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType.*;
import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycleRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycleResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseType;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.DefinePurpose;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.ElicitRequirements;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.IdentifyDomain;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.IdentifySubdomains;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.ProjectRepository;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.LifeCycleRepository;
import br.com.sabiox.sabiox_tool.services.ProjectAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class LifeCycleService {
    @Autowired
    ProjectAuthorizationService projectAuthorizationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LifeCycleRepository lifeCycleRepository;

    @Transactional
    public LifeCycleResponseDTO create(Long projectId, Long userId, LifeCycleRequestDTO lifeCycleRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        projectAuthorizationService.assertMember(projectId, userId);

        LifeCycle lifeCycle = new LifeCycle();
        lifeCycle.setPhase(project.getPhases().get(lifeCycleRequestDTO.phaseType()));
        project.getPhases().get(lifeCycleRequestDTO.phaseType()).getLifeCycles().add(lifeCycle);

        if (lifeCycleRequestDTO.phaseType() == PhaseType.REQUIREMENTS) {
            lifeCycle.getActivities().put(REQ_PURP, new DefinePurpose(REQ_PURP, lifeCycle));
            lifeCycle.getActivities().put(REQ_DOMN, new IdentifyDomain(REQ_DOMN, lifeCycle));
            lifeCycle.getActivities().put(REQ_ELIC, new ElicitRequirements(REQ_ELIC, lifeCycle));
            lifeCycle.getActivities().put(REQ_SUBD, new IdentifySubdomains(REQ_SUBD, lifeCycle));
        }

        lifeCycle = lifeCycleRepository.save(lifeCycle);

        return new LifeCycleResponseDTO(lifeCycle);
    }

    @Transactional(readOnly = true)
    public List<LifeCycleResponseDTO> getAllByPhaseType(
            Long userId,
            Long projectId,
            LifeCycleRequestDTO lifeCycleRequestDTO)

    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        projectAuthorizationService.assertMember(projectId, userId);

        List<LifeCycle> lifeCycles = project.getPhases().get(lifeCycleRequestDTO.phaseType()).getLifeCycles();
        return lifeCycles.stream().map(LifeCycleResponseDTO::new).toList();
    }

    @Transactional
    public void delete(Long userId, Long lifeCycleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        LifeCycle lifeCycle = lifeCycleRepository.findById(lifeCycleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "LifeCycle not found."
                ));

        projectAuthorizationService.assertMember(lifeCycle.getPhase().getProject().getId(), userId);

        lifeCycleRepository.delete(lifeCycle);
    }
}
