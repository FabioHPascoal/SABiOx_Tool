package br.com.sabiox.sabiox_tool.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.sabiox.sabiox_tool.services.ProjectService;
import br.com.sabiox.sabiox_tool.util.dtos.request.ProjectRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.ProjectResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(projectRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> readProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> readAllProjects() {
        return ResponseEntity.ok(projectService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updatePeca(@PathVariable Long id, @Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        return ResponseEntity.ok(projectService.update(id, projectRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}