package br.com.sabiox.sabiox_tool.services.sabiox.phases;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityStage;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.definePurpose.DefinePurpose;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.definePurpose.DefinePurposeRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.definePurpose.DefinePurposeResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.IdentifyDomain;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.IdentifyDomainRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.IdentifyDomainResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.ActivityRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.LifeCycleRepository;
import br.com.sabiox.sabiox_tool.services.ProjectAuthorizationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RequirementsActivityService {
    @Autowired
    private ProjectAuthorizationService projectAuthorizationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LifeCycleRepository lifeCycleRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Transactional
    public DefinePurposeResponseDTO getDefinePurpose (Long lifeCycleId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        LifeCycle lifeCycle = lifeCycleRepository.findById(lifeCycleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Life cycle not found."));

        Project project = lifeCycle.getPhase().getProject();

        projectAuthorizationService.assertMember(project.getId(), userId);

        DefinePurpose definePurpose = (DefinePurpose) lifeCycle.getActivities().get(ActivityType.REQ_PURP);

        return new DefinePurposeResponseDTO(definePurpose);
    }

    @Transactional
    public DefinePurposeResponseDTO editDefinePurpose (Long lifeCycleId, Long userId, DefinePurposeRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        LifeCycle lifeCycle = lifeCycleRepository.findById(lifeCycleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Life cycle not found."));

        Project project = lifeCycle.getPhase().getProject();

        projectAuthorizationService.assertMember(project.getId(), userId);

        DefinePurpose definePurpose = (DefinePurpose) lifeCycle.getActivities().get(ActivityType.REQ_PURP);
        if (request.whatQuestion() != null) definePurpose.setWhatQuestion(request.whatQuestion());
        if (request.forWhatQuestion() != null) definePurpose.setForWhatQuestion(request.forWhatQuestion());
        if (request.whyQuestion() != null) definePurpose.setWhyQuestion(request.whyQuestion());

        if (definePurpose.getWhatQuestion() != null
                || definePurpose.getForWhatQuestion() != null
                || definePurpose.getWhyQuestion() != null) {
            definePurpose.setActivityStage(ActivityStage.IN_PROGRESS);
        }

        return new DefinePurposeResponseDTO(activityRepository.save(definePurpose));
    }

    @Transactional
    public IdentifyDomainResponseDTO editIdentifyDomain (Long lifeCycleId, Long userId, IdentifyDomainRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        LifeCycle lifeCycle = lifeCycleRepository.findById(lifeCycleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Life cycle not found."));

        Project project = lifeCycle.getPhase().getProject();

        projectAuthorizationService.assertMember(project.getId(), userId);

        IdentifyDomain identifyDomain = (IdentifyDomain) lifeCycle.getActivities().get(ActivityType.REQ_DOMN);
        if (request.description() != null) identifyDomain.getDomain().setDescription(request.description());

        if (identifyDomain.getDomain().getDescription() != null) identifyDomain.setActivityStage(ActivityStage.IN_PROGRESS);

        return new IdentifyDomainResponseDTO(activityRepository.save(identifyDomain));
    }
}
