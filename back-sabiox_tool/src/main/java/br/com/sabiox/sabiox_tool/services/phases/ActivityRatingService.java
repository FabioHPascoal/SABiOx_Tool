package br.com.sabiox.sabiox_tool.services.phases;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.sabiox.sabiox_tool.model.phases.ActivityRating;
import br.com.sabiox.sabiox_tool.repository.phases.ActivityRatingRepository;
import br.com.sabiox.sabiox_tool.util.dtos.request.phases.ActivityRatingRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.ActivityRatingResponseDTO;
import br.com.sabiox.sabiox_tool.util.mappers.phases.ActivityRatingMapper;

@Service
public class ActivityRatingService {

    private final ActivityRatingRepository activityRatingRepository;

    public ActivityRatingService(ActivityRatingRepository activityRatingRepository) {
        this.activityRatingRepository = activityRatingRepository;
    }

    public ActivityRatingResponseDTO create(ActivityRatingRequestDTO activityRatingRequestDTO) {
        ActivityRating activityRating = new ActivityRating();
        
        BeanUtils.copyProperties(activityRatingRequestDTO, activityRating);
        ActivityRating activityRatingSaved = activityRatingRepository.save(activityRating);

        return ActivityRatingMapper.toDto(activityRatingSaved);
    }

    public ActivityRatingResponseDTO read(Long id) {
        ActivityRating activityRating = activityRatingRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActivityRating not found."));
       
        return ActivityRatingMapper.toDto(activityRating);
    }

    public List<ActivityRatingResponseDTO> readAll() {
        List<ActivityRating> activityRatings = activityRatingRepository.findAll();
        return ActivityRatingMapper.toDtoList(activityRatings);
    }

    public void delete(Long id) {
        ActivityRating activityRating = activityRatingRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActivityRating not found."));

        activityRatingRepository.delete(activityRating);
    }
}
