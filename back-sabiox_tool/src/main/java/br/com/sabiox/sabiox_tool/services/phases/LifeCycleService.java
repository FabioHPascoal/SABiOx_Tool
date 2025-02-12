package br.com.sabiox.sabiox_tool.services.phases;

import br.com.sabiox.sabiox_tool.model.phases.LifeCycle;
import br.com.sabiox.sabiox_tool.model.phases.Phase;
import br.com.sabiox.sabiox_tool.repository.phases.LifeCycleRepository;
import br.com.sabiox.sabiox_tool.repository.phases.PhaseRepository;
import br.com.sabiox.sabiox_tool.util.dtos.request.phases.LifeCycleRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.LifeCycleResponseDTO;
import br.com.sabiox.sabiox_tool.util.mappers.phases.LifeCycleMapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LifeCycleService {

    private final LifeCycleRepository lifeCycleRepository;
    private final PhaseRepository phaseRepository;

    public LifeCycleService(LifeCycleRepository lifeCycleRepository, PhaseRepository phaseRepository) {
        this.lifeCycleRepository = lifeCycleRepository;
        this.phaseRepository = phaseRepository;
    }

    public LifeCycleResponseDTO create(LifeCycleRequestDTO lifeCycleRequestDTO) {
        Phase phase = phaseRepository.findById(lifeCycleRequestDTO.phaseId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phase not found."));
        
        LifeCycle lifeCycle = new LifeCycle();
        
        lifeCycle.setPhase(phase);
        BeanUtils.copyProperties(lifeCycleRequestDTO, lifeCycle);
        LifeCycle lifeCycleSaved = lifeCycleRepository.save(lifeCycle);

        return LifeCycleMapper.toDto(lifeCycleSaved);
    }

    public LifeCycleResponseDTO read(Long id) {
        LifeCycle lifeCycle = lifeCycleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LifeCycle not found."));
       
        return LifeCycleMapper.toDto(lifeCycle);
    }

    public List<LifeCycleResponseDTO> readAll() {
        List<LifeCycle> lifeCycles = lifeCycleRepository.findAll();
        return LifeCycleMapper.toDtoList(lifeCycles);
    }

    public void delete(Long id) {
        LifeCycle lifeCycle = lifeCycleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LifeCycle not found."));

        lifeCycleRepository.delete(lifeCycle);
    }
}