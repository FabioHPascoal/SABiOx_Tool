package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.domain;

public record DomainDTO(
        Long id,
        String description,
        String horizontalDimension,
        String verticalDimension
) {
    public DomainDTO(Domain domain) {
        this(
                domain.getId(),
                domain.getDescription(),
                domain.getHorizontalDimension(),
                domain.getVerticalDimension()
        );
    }
}
