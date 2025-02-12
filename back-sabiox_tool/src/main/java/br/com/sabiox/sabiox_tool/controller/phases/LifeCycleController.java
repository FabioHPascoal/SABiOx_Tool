package br.com.sabiox.sabiox_tool.controller.phases;

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

import br.com.sabiox.sabiox_tool.services.phases.LifeCycleService;
import br.com.sabiox.sabiox_tool.util.dtos.request.phases.LifeCycleRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.LifeCycleResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/life_cycles")
public class LifeCycleController {

    private final LifeCycleService lifeCycleService;

    public LifeCycleController(final LifeCycleService lifeCycleService) {
        this.lifeCycleService = lifeCycleService;
    }

    @PostMapping
    public ResponseEntity<LifeCycleResponseDTO> createLifeCycle(@Valid @RequestBody LifeCycleRequestDTO lifeCycleRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lifeCycleService.create(lifeCycleRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LifeCycleResponseDTO> readLifeCycle(@PathVariable Long id) {
        return ResponseEntity.ok(lifeCycleService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<LifeCycleResponseDTO>> readAllLifeCycles() {
        return ResponseEntity.ok(lifeCycleService.readAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLifeCycle(@PathVariable Long id) {
        lifeCycleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}