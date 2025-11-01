package br.com.sabiox.sabiox_tool.controller.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.definePurpose.DefinePurposeRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.definePurpose.DefinePurposeResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.services.sabiox.phases.requirements.RequirementsActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/project/life_cycle/{lifeCycleId}")
public class DefinePurposeController {
    @Autowired
    RequirementsActivityService requirementsActivityService;

    @PutMapping("/define_purpose")
    public ResponseEntity<DefinePurposeResponseDTO> editDefinePurpose
            (@PathVariable Long lifeCycleId,
             @Valid @RequestBody DefinePurposeRequestDTO request,
             @AuthenticationPrincipal User authUser) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requirementsActivityService.editDefinePurpose(lifeCycleId, authUser.getId(), request));
    }

    @GetMapping("/define_purpose")
    public ResponseEntity<DefinePurposeResponseDTO> getDefinePurpose(
            @PathVariable Long lifeCycleId,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.ok(requirementsActivityService.getDefinePurpose(lifeCycleId, authUser.getId()));
    }
}
