package br.com.sabiox.sabiox_tool.controller.sabiox;

import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.services.sabiox.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class PhaseController {
    @Autowired
    PhaseService phaseService;

    @GetMapping("/{projectId}/phase")
    public ResponseEntity<List<PhaseResponseDTO>> getAllPhases(
            @PathVariable Long projectId,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.status(HttpStatus.OK).body(phaseService.getAllPhases(authUser.getId(), projectId));
    }
}
