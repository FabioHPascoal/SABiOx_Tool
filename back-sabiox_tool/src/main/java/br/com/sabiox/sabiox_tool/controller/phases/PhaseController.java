package br.com.sabiox.sabiox_tool.controller.phases;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sabiox.sabiox_tool.services.phases.PhaseService;
import br.com.sabiox.sabiox_tool.util.dtos.request.phases.PhaseRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.PhaseResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/phases")
public class PhaseController {

    private final PhaseService phaseService;

    public PhaseController(final PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    @PostMapping
    public ResponseEntity<PhaseResponseDTO> createPhase(@Valid @RequestBody PhaseRequestDTO phaseRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(phaseService.create(phaseRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhaseResponseDTO> readPhase(@PathVariable Long id) {
        return ResponseEntity.ok(phaseService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<PhaseResponseDTO>> readAllPhases() {
        return ResponseEntity.ok(phaseService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhaseResponseDTO> updatePeca(@PathVariable Long id, @Valid @RequestBody PhaseRequestDTO phaseRequestDTO) {
        return ResponseEntity.ok(phaseService.update(id, phaseRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhase(@PathVariable Long id) {
        phaseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
