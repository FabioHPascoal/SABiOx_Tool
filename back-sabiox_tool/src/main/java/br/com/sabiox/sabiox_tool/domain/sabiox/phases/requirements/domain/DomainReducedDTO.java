package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.domain;

public record DomainReducedDTO(
        Long id,
        String description
) {
    public DomainReducedDTO(Domain domain) {
        this(
                domain.getId(),
                domain.getDescription()
        );
    }
}
