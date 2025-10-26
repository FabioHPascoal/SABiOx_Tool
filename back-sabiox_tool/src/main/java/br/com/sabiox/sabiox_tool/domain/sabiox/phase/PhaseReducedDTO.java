package br.com.sabiox.sabiox_tool.domain.sabiox.phase;

public record PhaseReducedDTO(
        Long phaseId,
        PhaseType phaseType)
{
    public PhaseReducedDTO(Phase phase) {
        this(
                phase.getId(),
                phase.getPhaseType());
    }
}
