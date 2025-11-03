package br.com.sabiox.sabiox_tool.controller.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.IdentifyDomainRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.IdentifyDomainResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.services.sabiox.phases.requirements.RequirementsActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project/life_cycle/{lifeCycleId}")
public class IdentifyDomainController {
    @Autowired
    RequirementsActivityService requirementsActivityService;

    @PutMapping("/identify_domain")
    public ResponseEntity<IdentifyDomainResponseDTO> editIdentifyDomain
            (@PathVariable Long lifeCycleId,
             @Valid @RequestBody IdentifyDomainRequestDTO request,
             @AuthenticationPrincipal User authUser) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requirementsActivityService.editIdentifyDomain(lifeCycleId, authUser.getId(), request));
    }

    @GetMapping("/identify_domain")
    public ResponseEntity<IdentifyDomainResponseDTO> getDefinePurpose(
            @PathVariable Long lifeCycleId,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.ok(requirementsActivityService.getIdentifyDomain(lifeCycleId, authUser.getId()));
    }
}
