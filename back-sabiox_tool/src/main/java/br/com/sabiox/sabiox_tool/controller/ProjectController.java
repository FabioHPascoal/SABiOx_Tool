package br.com.sabiox.sabiox_tool.controller;

import java.util.List;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.project.ProjectRequestDTO;
import br.com.sabiox.sabiox_tool.domain.project.ProjectResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.sabiox.sabiox_tool.services.ProjectService;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("project")
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        Project project = projectService.create(projectRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProjectResponseDTO(project));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Long id) {
        Project project = projectService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ProjectResponseDTO(project));
    }

    @GetMapping("/admin/projects")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<Project> projects = projectService.getAll();
        return ResponseEntity.ok(projectService.getAll().stream().map(ProjectResponseDTO::new).toList());
    }

    @GetMapping("/projects/enabled")
    public ResponseEntity<List<ProjectResponseDTO>> getAllEnabledProjects() {
        List<Project> projects = projectService.getAllEnabled();
        return ResponseEntity.ok(projectService.getAllEnabled().stream().map(ProjectResponseDTO::new).toList());
    }

    @PutMapping("/project/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id,
                                                            @Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        Project project = projectService.update(id, projectRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ProjectResponseDTO(project));
    }

    @PutMapping("/project/disable/{id}")
    public ResponseEntity<Void> disableProject(@PathVariable Long id) {
        projectService.disable(id);
        return ResponseEntity.noContent().build();
    }

}
