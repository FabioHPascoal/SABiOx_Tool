package br.com.sabiox.sabiox_tool.controller;

import java.util.List;

import br.com.sabiox.sabiox_tool.domain.project.*;
import br.com.sabiox.sabiox_tool.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO,
                                                            @AuthenticationPrincipal User authUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.create(projectRequestDTO, authUser.getEmail()));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.get(id));
    }

    @GetMapping("/admin/projects")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAll());
    }

    @GetMapping("/projects/enabled")
    public ResponseEntity<List<ProjectResponseReducedDTO>> getAllEnabledProjects() {
        return ResponseEntity.ok(projectService.getAllEnabled());
    }

    @PutMapping("/project/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long projectId,
                                                            @AuthenticationPrincipal User authUser,
                                                            @Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        Project project = projectService.update(projectId, authUser.getId(), projectRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ProjectResponseDTO(project));
    }

    @PutMapping("/project/disable/{id}")
    public ResponseEntity<Void> disableProject(@AuthenticationPrincipal User authUser,@PathVariable Long id) {
        projectService.disable(id, authUser.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/project/{id}")
    public ResponseEntity<Void> addMember(@AuthenticationPrincipal User authUser,
                                          @PathVariable Long id,
                                          @RequestBody AddMemberRequestDTO request) {

        projectService.addMember(authUser.getId(), id, request.email());
        return ResponseEntity.noContent().build();
    }

}
