package br.com.sabiox.sabiox_tool.controller.phases.requirements;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sabiox.sabiox_tool.services.phases.requirements.RequirementActivityService;
import br.com.sabiox.sabiox_tool.util.dtos.request.phases.requirements.RequirementActivityRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.requirements.RequirementActivityResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/requirement_activities")
public class RequirementActivityController {

    private final RequirementActivityService requirementActivityService;

    public RequirementActivityController(final RequirementActivityService requirementActivityService) {
        this.requirementActivityService = requirementActivityService;
    }

    @PostMapping
    public ResponseEntity<RequirementActivityResponseDTO> createRequirementActivity(@Valid @RequestBody RequirementActivityRequestDTO requirementActivityRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requirementActivityService.create(requirementActivityRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequirementActivityResponseDTO> readRequirementActivity(@PathVariable Long id) {
        return ResponseEntity.ok(requirementActivityService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<RequirementActivityResponseDTO>> readAllRequirementActivitys() {
        return ResponseEntity.ok(requirementActivityService.readAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequirementActivity(@PathVariable Long id) {
        requirementActivityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
