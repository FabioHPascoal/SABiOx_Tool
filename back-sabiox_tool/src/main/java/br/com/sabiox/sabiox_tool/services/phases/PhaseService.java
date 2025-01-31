package br.com.sabiox.sabiox_tool.services.phases;

import br.com.sabiox.sabiox_tool.model.phases.Phase;
import br.com.sabiox.sabiox_tool.model.Project;
import br.com.sabiox.sabiox_tool.repository.phases.PhaseRepository;
import br.com.sabiox.sabiox_tool.repository.ProjectRepository;
import br.com.sabiox.sabiox_tool.util.dtos.request.phases.PhaseRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.phases.PhaseResponseDTO;
import br.com.sabiox.sabiox_tool.util.mappers.phases.PhaseMapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final ProjectRepository projectRepository;

    public PhaseService(PhaseRepository phaseRepository, ProjectRepository projectRepository) {
        this.phaseRepository = phaseRepository;
        this.projectRepository = projectRepository;
    }

    public PhaseResponseDTO create(PhaseRequestDTO phaseRequestDTO) {
        Project project = projectRepository.findById(phaseRequestDTO.projectId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
            
        Phase phase = new Phase();
        
        phase.setProject(project);
        BeanUtils.copyProperties(phaseRequestDTO, phase);
        Phase phaseSaved = phaseRepository.save(phase);

        System.out.println(phase);
        return PhaseMapper.toDto(phaseSaved);
    }

    public PhaseResponseDTO read(Long id) {
        Phase phase = phaseRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phase not found."));
       
        return PhaseMapper.toDto(phase);
    }

    public List<PhaseResponseDTO> readAll() {
        List<Phase> phases = phaseRepository.findAll();
        return PhaseMapper.toDtoList(phases);
    }

    public PhaseResponseDTO update(Long id, PhaseRequestDTO phaseRequestDTO) {
        Phase phase = phaseRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phase not found."));
        
        BeanUtils.copyProperties(phaseRequestDTO, phase);
        Phase phaseUpdated = phaseRepository.save(phase);
        return PhaseMapper.toDto(phaseUpdated);
    }

    public void delete(Long id) {
        Phase phase = phaseRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));

        phaseRepository.delete(phase);
    }
}