package br.com.sabiox.sabiox_tool.controller.sabiox;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityStageDTO;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseResponseDTO;
import br.com.sabiox.sabiox_tool.domain.user.User;
import br.com.sabiox.sabiox_tool.repositories.sabiox.ActivityRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/project")
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;

    @PutMapping("/activity/{activityId}")
    public ResponseEntity<List<PhaseResponseDTO>> changeActivityStage(
            @PathVariable Long activityId,
            @AuthenticationPrincipal User authUser,
            @Valid @RequestBody ActivityStageDTO activityStageDTO) {

        Activity activity = activityRepository.findById(activityId).orElseThrow();
        activity.setActivityStage(activityStageDTO.activityStage());
        activityRepository.save(activity);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}