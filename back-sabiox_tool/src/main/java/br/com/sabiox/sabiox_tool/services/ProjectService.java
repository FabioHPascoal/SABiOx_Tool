package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.project.ProjectRequestDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseType;
import br.com.sabiox.sabiox_tool.repositories.ProjectRepository;
import br.com.sabiox.sabiox_tool.repositories.UserRepository;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project create(ProjectRequestDTO projectRequestDTO) {
        User user = userRepository.findById(projectRequestDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        if (!user.isEnabled()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not enabled.");

        Project project = getProject(projectRequestDTO, user);

        return projectRepository.save(project);
    }

    private static Project getProject(ProjectRequestDTO projectRequestDTO, User user) {
        Project project = new Project();
        project.setUser(user);
        project.setTitle(projectRequestDTO.title());
        project.setDescription(projectRequestDTO.description());
        project.setIsEnabled(true);

        List<Phase> phases = List.of(
                new Phase(null, project, PhaseType.REQUIREMENTS),
                new Phase(null, project, PhaseType.SETUP),
                new Phase(null, project, PhaseType.CAPTURE),
                new Phase(null, project, PhaseType.DESIGN),
                new Phase(null, project, PhaseType.IMPLEMENTATION)
        );

        project.setPhases(phases);
        return project;
    }

    public Project get(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public List<Project> getAllEnabled() {
        return projectRepository.findAll()
                .stream().filter(Project::getIsEnabled)
                .toList();
    }

    public Project update(Long id, ProjectRequestDTO projectRequestDTO) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
        
        BeanUtils.copyProperties(projectRequestDTO, project);
        return projectRepository.save(project);
    }

    public void disable(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        project.setIsEnabled(false);
        projectRepository.save(project);
    }
}