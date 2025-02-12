package br.com.sabiox.sabiox_tool.services.phases.requirements;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.sabiox.sabiox_tool.model.phases.LifeCycle;
import br.com.sabiox.sabiox_tool.model.phases.Phase;
import br.com.sabiox.sabiox_tool.model.phases.requirements.RequirementActivity;
import br.com.sabiox.sabiox_tool.repository.phases.LifeCycleRepository;
import br.com.sabiox.sabiox_tool.repository.phases.PhaseRepository;
import br.com.sabiox.sabiox_tool.repository.phases.requirements.RequirementActivityRepository;
import br.com.sabiox.sabiox_tool.util.dtos.request.phases.requirements.RequirementActivityRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.requirements.RequirementActivityResponseDTO;
import br.com.sabiox.sabiox_tool.util.mappers.phases.requirements.RequirementActivityMapper;

@Service
public class RequirementActivityService {

    private final RequirementActivityRepository requirementActivityRepository;
    private final PhaseRepository phaseRepository;
    private final LifeCycleRepository lifeCycleRepository;

    public RequirementActivityService(RequirementActivityRepository requirementActivityRepository, 
                                      PhaseRepository phaseRepository,
                                      LifeCycleRepository lifeCycleRepository) {
        this.requirementActivityRepository = requirementActivityRepository;
        this.phaseRepository = phaseRepository;
        this.lifeCycleRepository = lifeCycleRepository;
    }

    public RequirementActivityResponseDTO create(RequirementActivityRequestDTO requirementActivityRequestDTO) {
        Phase phase = phaseRepository.findById(requirementActivityRequestDTO.phaseId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phase not found."));
        LifeCycle lifeCycle = lifeCycleRepository.findById(requirementActivityRequestDTO.lifeCycleId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LifeCycle not found."));
            
        RequirementActivity requirementActivity = new RequirementActivity();
        
        requirementActivity.setPhase(phase);
        requirementActivity.setLifeCycle(lifeCycle);

        BeanUtils.copyProperties(requirementActivityRequestDTO, requirementActivity);
        RequirementActivity requirementActivitySaved = requirementActivityRepository.save(requirementActivity);

        return RequirementActivityMapper.toDto(requirementActivitySaved);
    }

    public RequirementActivityResponseDTO read(Long id) {
        RequirementActivity requirementActivity = requirementActivityRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requirement activity not found."));
       
        return RequirementActivityMapper.toDto(requirementActivity);
    }

    public List<RequirementActivityResponseDTO> readAll() {
        List<RequirementActivity> requirementActivitys = requirementActivityRepository.findAll();
        return RequirementActivityMapper.toDtoList(requirementActivitys);
    }

    public void delete(Long id) {
        RequirementActivity requirementActivity = requirementActivityRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requirement activity not found."));

        requirementActivityRepository.delete(requirementActivity);
    }   
}