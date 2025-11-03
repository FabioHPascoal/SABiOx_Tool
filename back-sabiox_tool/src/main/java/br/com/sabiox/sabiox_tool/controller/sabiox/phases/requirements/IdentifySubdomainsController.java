package br.com.sabiox.sabiox_tool.controller.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.IdentifySubdomainsRequestDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.IdentifySubdomainsResponseDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.subdomain.SubdomainDTO;
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
public class IdentifySubdomainsController {
    @Autowired
    RequirementsActivityService requirementsActivityService;

    @PutMapping("/identify_subdomains")
    public ResponseEntity<SubdomainDTO> addSubdomain
            (@PathVariable Long lifeCycleId,
             @Valid @RequestBody IdentifySubdomainsRequestDTO request,
             @AuthenticationPrincipal User authUser) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requirementsActivityService.addSubdomain(lifeCycleId, authUser.getId(), request));
    }

    @GetMapping("/identify_subdomains")
    public ResponseEntity<IdentifySubdomainsResponseDTO> getIdentifySubdomains(
            @PathVariable Long lifeCycleId,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.ok(requirementsActivityService.getIdentifySubdomains(lifeCycleId, authUser.getId()));
    }
}
