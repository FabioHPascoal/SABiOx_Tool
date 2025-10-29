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
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.IdentifySubdomains;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.IdentifySubdomainsRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.IdentifySubdomainsResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.subdomain.Subdomain;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.subdomain.SubdomainDTO;
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

    private LifeCycle checkUserProject(Long lifeCycleId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        LifeCycle lifeCycle = lifeCycleRepository.findById(lifeCycleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Life cycle not found."));

        Project project = lifeCycle.getPhase().getProject();
        projectAuthorizationService.assertMember(project.getId(), userId);
        return lifeCycle;
    }

    @Transactional
    public DefinePurposeResponseDTO editDefinePurpose (Long lifeCycleId, Long userId, DefinePurposeRequestDTO request) {
        LifeCycle lifeCycle = checkUserProject(lifeCycleId, userId);

        DefinePurpose definePurpose = (DefinePurpose) lifeCycle.getActivities().get(ActivityType.REQ_PURP);
        boolean updated = false;

        if (request.whatQuestion() != null) {definePurpose.setWhatQuestion(request.whatQuestion()); updated = true;}
        if (request.forWhatQuestion() != null) {definePurpose.setForWhatQuestion(request.forWhatQuestion()); updated = true;}
        if (request.whyQuestion() != null) {definePurpose.setWhyQuestion(request.whyQuestion()); updated = true;}

        if (updated) definePurpose.setActivityStage(ActivityStage.IN_PROGRESS);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty body.");

        return new DefinePurposeResponseDTO(activityRepository.save(definePurpose));
    }

    @Transactional
    public DefinePurposeResponseDTO getDefinePurpose (Long lifeCycleId, Long userId) {
        LifeCycle lifeCycle = checkUserProject(lifeCycleId, userId);
        DefinePurpose definePurpose = (DefinePurpose) lifeCycle.getActivities().get(ActivityType.REQ_PURP);
        return new DefinePurposeResponseDTO(definePurpose);
    }

    @Transactional
    public IdentifyDomainResponseDTO editIdentifyDomain (Long lifeCycleId, Long userId, IdentifyDomainRequestDTO request) {
        LifeCycle lifeCycle = checkUserProject(lifeCycleId, userId);

        IdentifyDomain identifyDomain = (IdentifyDomain) lifeCycle.getActivities().get(ActivityType.REQ_DOMN);
        boolean updated = false;

        if (request.description() != null) {
            identifyDomain.getDomain().setDescription(request.description());
            updated = true;
        }
        if (request.horizontalDimension() != null) {
            identifyDomain.getDomain().setHorizontalDimension(request.horizontalDimension());
            updated = true;
        }
        if (request.verticalDimension() != null) {
            identifyDomain.getDomain().setVerticalDimension(request.verticalDimension());
            updated = true;
        }

        if (updated) identifyDomain.setActivityStage(ActivityStage.IN_PROGRESS);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty body.");

        return new IdentifyDomainResponseDTO(activityRepository.save(identifyDomain));
    }

    @Transactional
    public IdentifyDomainResponseDTO getIdentifyDomain (Long lifeCycleId, Long userId) {
        LifeCycle lifeCycle = checkUserProject(lifeCycleId, userId);
        IdentifyDomain identifyDomain = (IdentifyDomain) lifeCycle.getActivities().get(ActivityType.REQ_DOMN);
        return new IdentifyDomainResponseDTO(identifyDomain);
    }

    @Transactional
    public SubdomainDTO addSubdomain (Long lifeCycleId, Long userId, IdentifySubdomainsRequestDTO request) {
        LifeCycle lifeCycle = checkUserProject(lifeCycleId, userId);

        IdentifySubdomains identifySubdomains = (IdentifySubdomains) lifeCycle.getActivities().get(ActivityType.REQ_SUBD);

        Subdomain subdomain = new Subdomain();
        subdomain.setTitle(request.title());
        subdomain.setDescription(request.description());
        subdomain.setIdentifySubdomains(identifySubdomains);

        identifySubdomains.getSubdomains().add(subdomain);
        identifySubdomains.setActivityStage(ActivityStage.IN_PROGRESS);
        identifySubdomains = activityRepository.save(identifySubdomains);

        return new SubdomainDTO(identifySubdomains.getSubdomains().getLast());
    }

    @Transactional
    public IdentifySubdomainsResponseDTO getIdentifySubdomains (Long lifeCycleId, Long userId) {
        LifeCycle lifeCycle = checkUserProject(lifeCycleId, userId);
        IdentifySubdomains identifySubdomains = (IdentifySubdomains) lifeCycle.getActivities().get(ActivityType.REQ_SUBD);
        return new IdentifySubdomainsResponseDTO(identifySubdomains);
    }

//    @Transactional
//    public void deleteSubdomain(Long lifeCycleId, Long subdomainId, Long userId) {
//        LifeCycle lifeCycle = checkUserProject(lifeCycleId, userId);
//
//        IdentifySubdomains identifySubdomains = (IdentifySubdomains) lifeCycle.getActivities().get(ActivityType.REQ_SUBD);
//
//        lifeCycleRepository.delete(lifeCycle);
//    }
}
