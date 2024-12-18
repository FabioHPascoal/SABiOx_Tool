package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.model.SABiOxProject;
import br.com.sabiox.sabiox_tool.repository.ProjectRepository;
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

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponseDTO create(ProjectRequestDTO projectRequestDTO) {
        SABiOxProject project = new SABiOxProject(projectRequestDTO.title(), 
                                      projectRequestDTO.description());

        BeanUtils.copyProperties(projectRequestDTO, project);
        SABiOxProject projectSaved = projectRepository.save(project);

        return ProjectMapper.toDto(projectSaved);
    }

    public ProjectResponseDTO read(Long id) {
        SABiOxProject project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));
       
        return ProjectMapper.toDto(project);
    }

    public List<ProjectResponseDTO> readAll() {
        List<SABiOxProject> projects = projectRepository.findAll();
        return ProjectMapper.toDtoList(projects);
    }

    public ProjectResponseDTO update(Long id, ProjectRequestDTO projectRequestDTO) {
        SABiOxProject project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));
        
        BeanUtils.copyProperties(projectRequestDTO, project);
        SABiOxProject projectUpdated = projectRepository.save(project);
        return ProjectMapper.toDto(projectUpdated);
    }

    public void delete(Long id) {
        SABiOxProject project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));

        projectRepository.delete(project);
    }
}