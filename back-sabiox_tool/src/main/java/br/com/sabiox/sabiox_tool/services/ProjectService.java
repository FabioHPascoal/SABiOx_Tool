package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.model.Project;
import br.com.sabiox.sabiox_tool.model.User;
import br.com.sabiox.sabiox_tool.repository.ProjectRepository;
import br.com.sabiox.sabiox_tool.repository.UserRepository;
import br.com.sabiox.sabiox_tool.util.dtos.request.ProjectRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.ProjectResponseDTO;
import br.com.sabiox.sabiox_tool.util.mappers.ProjectMapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectResponseDTO create(ProjectRequestDTO projectRequestDTO) {
        User user = userRepository.findById(projectRequestDTO.userId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
            
        Project project = new Project();
        
        project.setUser(user);
        BeanUtils.copyProperties(projectRequestDTO, project);
        Project projectSaved = projectRepository.save(project);

        System.out.println(project);
        return ProjectMapper.toDto(projectSaved);
    }

    public ProjectResponseDTO read(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
       
        return ProjectMapper.toDto(project);
    }

    public List<ProjectResponseDTO> readAll() {
        List<Project> projects = projectRepository.findAll();
        return ProjectMapper.toDtoList(projects);
    }

    public ProjectResponseDTO update(Long id, ProjectRequestDTO projectRequestDTO) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
        
        BeanUtils.copyProperties(projectRequestDTO, project);
        Project projectUpdated = projectRepository.save(project);
        return ProjectMapper.toDto(projectUpdated);
    }

    public void delete(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));

        projectRepository.delete(project);
    }
}