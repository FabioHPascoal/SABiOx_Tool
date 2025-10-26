package br.com.sabiox.sabiox_tool.services.sabiox;

import static br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType.*;
import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycleRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycleResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseType;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.definePurpose.DefinePurpose;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.ElicitRequirements;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.IdentifyDomain;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.IdentifySubdomains;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.ProjectRepository;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.ActivityRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.LifeCycleRepository;
import br.com.sabiox.sabiox_tool.services.ProjectAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LifeCycleService {
    @Autowired
    private ProjectAuthorizationService projectAuthorizationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LifeCycleRepository lifeCycleRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Transactional
    public LifeCycleResponseDTO create(Long projectId, Long userId, LifeCycleRequestDTO lifeCycleRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        projectAuthorizationService.assertMember(projectId, userId);

        LifeCycle lifeCycle = new LifeCycle();
        lifeCycle.setPhase(project.getPhases().get(lifeCycleRequestDTO.phaseType()));

        project.getPhases().get(lifeCycleRequestDTO.phaseType()).getLifeCycles().add(lifeCycle);

        lifeCycle = lifeCycleRepository.save(lifeCycle);

        if (lifeCycleRequestDTO.phaseType() == PhaseType.REQUIREMENTS) {
            List<Activity> orderedActivities = List.of(
                    new DefinePurpose(REQ_PURP, lifeCycle),
                    new IdentifyDomain(REQ_DOMN, lifeCycle),
                    new ElicitRequirements(REQ_ELIC, lifeCycle),
                    new IdentifySubdomains(REQ_SUBD, lifeCycle)
            );

            for (Activity activity : orderedActivities) {
                activityRepository.save(activity);
                lifeCycle.getActivities().put(activity.getActivityType(), activity);
            }
        }

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
