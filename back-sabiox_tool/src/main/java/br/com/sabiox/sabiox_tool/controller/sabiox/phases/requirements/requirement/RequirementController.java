package br.com.sabiox.sabiox_tool.controller.sabiox.phases.requirements.requirement;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.RequirementRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.RequirementResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.services.sabiox.phases.requirements.requirement.RequirementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project/life_cycle/{lifeCycleId}")
public class RequirementController {
    @Autowired
    RequirementService requirementService;

    @PostMapping("/requirement")
    public ResponseEntity<RequirementResponseDTO> createRequirement(
            @PathVariable Long lifeCycleId,
            @Valid @RequestBody RequirementRequestDTO request,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requirementService.createRequirement(lifeCycleId, authUser.getId(), request));
    }
}
