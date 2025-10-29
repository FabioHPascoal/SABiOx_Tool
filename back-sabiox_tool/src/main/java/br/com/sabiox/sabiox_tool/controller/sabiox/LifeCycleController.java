package br.com.sabiox.sabiox_tool.controller.sabiox;

import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycleRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycleResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.services.sabiox.LifeCycleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/project")
public class LifeCycleController {
    @Autowired
    LifeCycleService lifeCycleService;

    @PostMapping("/{projectId}/lifeCycle")
    public ResponseEntity<LifeCycleResponseDTO> createLifeCycle(
            @PathVariable Long projectId,
            @Valid @RequestBody LifeCycleRequestDTO lifeCycleRequestDTO,
            @AuthenticationPrincipal User authUser) {

        LifeCycleResponseDTO response = lifeCycleService.create(projectId, authUser.getId(), lifeCycleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{projectId}/lifeCycle")
    public ResponseEntity<List<LifeCycleResponseDTO>> getLifeCyclesByPhaseType(
            @PathVariable Long projectId,
            @Valid @RequestBody LifeCycleRequestDTO lifeCycleRequestDTO,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(lifeCycleService.getAllByPhaseType(authUser.getId(), projectId, lifeCycleRequestDTO));
    }

    @GetMapping("/lifeCycle/{lifeCycleId}")
    public ResponseEntity<LifeCycleResponseDTO> getLifeCycle(
            @PathVariable Long lifeCycleId,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(lifeCycleService.get(authUser.getId(), lifeCycleId));
    }

    @DeleteMapping("/lifeCycle/{lifeCycleId}")
    public ResponseEntity<Void> deleteLifeCycle(
            @PathVariable Long lifeCycleId,
            @AuthenticationPrincipal User authUser) {

        lifeCycleService.delete(authUser.getId(), lifeCycleId);
        return ResponseEntity.noContent().build();
    }
}