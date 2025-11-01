package br.com.sabiox.sabiox_tool.services.sabiox.phases.requirements.requirement;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.ElicitRequirements;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.Requirement;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.RequirementRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.RequirementResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.LifeCycleRepository;
import br.com.sabiox.sabiox_tool.repositories.sabiox.phases.requirements.RequirementRepository;
import br.com.sabiox.sabiox_tool.services.ProjectAuthorizationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RequirementService {
    @Autowired
    RequirementRepository requirementRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LifeCycleRepository lifeCycleRepository;

    @Autowired
    ProjectAuthorizationService projectAuthorizationService;

    private ElicitRequirements checkUserProject(Long lifeCycleId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        LifeCycle lifeCycle = lifeCycleRepository.findById(lifeCycleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Life cycle not found."));

        Project project = lifeCycle.getPhase().getProject();
        projectAuthorizationService.assertMember(project.getId(), userId);
        return (ElicitRequirements) lifeCycle.getActivities().get(ActivityType.REQ_ELIC);
    }

    @Transactional
    public RequirementResponseDTO createRequirement(
            Long lifeCycleId, Long userId, RequirementRequestDTO request) {

        ElicitRequirements elicitRequirements = checkUserProject(lifeCycleId, userId);

        Requirement requirement = new Requirement();
        requirement.setElicitRequirements(elicitRequirements);
        requirement.setRequirementType(request.requirementType());
        requirement.setUser(userRepository.getReferenceById(userId));
        requirement.setDescription(request.description());

        return new RequirementResponseDTO(requirementRepository.save(requirement));
    }
}
