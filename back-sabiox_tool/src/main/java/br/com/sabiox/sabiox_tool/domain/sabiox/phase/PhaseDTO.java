package br.com.sabiox.sabiox_tool.domain.sabiox.phase;

public record PhaseDTO(Long phaseId, PhaseType phaseType) {
    public PhaseDTO(Phase phase) {
        this(phase.getId(), phase.getPhaseType());
    }
}
