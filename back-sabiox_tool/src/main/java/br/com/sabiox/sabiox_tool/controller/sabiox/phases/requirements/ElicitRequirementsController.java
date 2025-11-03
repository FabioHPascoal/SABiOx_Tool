package br.com.sabiox.sabiox_tool.controller.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.ElicitRequirementsResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.services.sabiox.phases.requirements.RequirementsActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/life_cycle/{lifeCycleId}")
public class ElicitRequirementsController {
    @Autowired
    RequirementsActivityService requirementsActivityService;

    @GetMapping("/elicit_requirements")
    public ResponseEntity<ElicitRequirementsResponseDTO> getElicitRequirements(
            @PathVariable Long lifeCycleId,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.ok(requirementsActivityService.getElicitRequirements(lifeCycleId, authUser.getId()));
    }
}
