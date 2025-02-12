package br.com.sabiox.sabiox_tool.controller.phases;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.sabiox.sabiox_tool.services.phases.ActivityRatingService;
import br.com.sabiox.sabiox_tool.util.dtos.request.phases.ActivityRatingRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.ActivityRatingResponseDTO;
import jakarta.validation.Valid;

public class ActivityRatingController {
    private final ActivityRatingService activityRatingService;

    public ActivityRatingController(final ActivityRatingService activityRatingService) {
        this.activityRatingService = activityRatingService;
    }

    @PostMapping
    public ResponseEntity<ActivityRatingResponseDTO> createActivityRating(@Valid @RequestBody ActivityRatingRequestDTO activityRatingRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityRatingService.create(activityRatingRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityRatingResponseDTO> readActivityRating(@PathVariable Long id) {
        return ResponseEntity.ok(activityRatingService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<ActivityRatingResponseDTO>> readAllActivityRatings() {
        return ResponseEntity.ok(activityRatingService.readAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityRating(@PathVariable Long id) {
        activityRatingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
