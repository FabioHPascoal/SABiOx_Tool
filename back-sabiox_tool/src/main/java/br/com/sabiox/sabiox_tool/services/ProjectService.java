package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ParticipationType;
import br.com.sabiox.sabiox_tool.domain.ProjectUser.ProjectUser;
import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.project.ProjectRequestDTO;
import br.com.sabiox.sabiox_tool.domain.project.ProjectResponseDTO;
import br.com.sabiox.sabiox_tool.domain.project.ProjectResponseReducedDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseType;
import br.com.sabiox.sabiox_tool.repositories.ProjectRepository;
import br.com.sabiox.sabiox_tool.repositories.ProjectUserRepository;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;

import java.util.List;

import br.com.sabiox.sabiox_tool.repositories.sabiox.PhaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectService {
    @Autowired
    ProjectAuthorizationService projectAuthorizationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PhaseRepository phaseRepository;

    @Transactional
    public ProjectResponseDTO create(ProjectRequestDTO projectRequestDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        Project project = new Project();
        project.setTitle(projectRequestDTO.title());
        project.setDescription(projectRequestDTO.description());
        project.setIsEnabled(true);

        ProjectUser projectOwner = new ProjectUser();
        projectOwner.setUser(user);
        projectOwner.setProject(project);
        projectOwner.setParticipationType(ParticipationType.OWNER);
        projectOwner.setEmail(userEmail);

        project.getParticipants().add(projectOwner);

        project = projectRepository.save(project);

        List<PhaseType> orderedPhases = List.of(
                PhaseType.REQUIREMENTS,
                PhaseType.SETUP,
                PhaseType.CAPTURE,
                PhaseType.DESIGN,
                PhaseType.IMPLEMENTATION
        );

        for (PhaseType type : orderedPhases) {
            Phase phase = new Phase(project, type);
            phaseRepository.save(phase);
            project.getPhases().put(type, phase);
        }

        return new ProjectResponseDTO(project);
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO get(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        return new ProjectResponseDTO(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getAll() {
        List<Project> projects = projectRepository.findAll(
                Sort.by(Sort.Direction.DESC, "creationDate").and(
                Sort.by(Sort.Direction.ASC, "title")));

        return projects.stream().map(ProjectResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseReducedDTO> getAllEnabled() {
        List<Project> projects = projectRepository.findByIsEnabledTrue(
                Sort.by(Sort.Direction.DESC, "creationDate").and(
                Sort.by(Sort.Direction.ASC, "title")));

        return projects.stream().map(ProjectResponseReducedDTO::new).toList();
    }

    @Transactional
    public Project update(Long projectId, Long userId, ProjectRequestDTO projectRequestDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        projectAuthorizationService.assertOwner(projectId, userId);

        BeanUtils.copyProperties(projectRequestDTO, project);
        return projectRepository.save(project);
    }


    @Transactional
    public void disable(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        projectAuthorizationService.assertOwner(projectId, userId);

        project.setIsEnabled(false);
        projectRepository.save(project);
    }

    @Transactional
    public void addMember(Long userId, Long projectId, String email) {
        User addedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member user not found."));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        projectAuthorizationService.assertOwner(projectId, userId);

        ProjectUser newMember = new ProjectUser();
        newMember.setUser(addedUser);
        newMember.setProject(project);
        newMember.setParticipationType(ParticipationType.MEMBER);
        newMember.setEmail(email);

        project.getParticipants().add(newMember);

        projectRepository.save(project);
    }
}