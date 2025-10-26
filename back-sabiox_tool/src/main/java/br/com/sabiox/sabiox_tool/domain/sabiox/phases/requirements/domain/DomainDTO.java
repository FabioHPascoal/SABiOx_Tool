package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.domain;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.IdentifyDomain;

import java.util.List;

public record DomainDTO(
        Long id,
        Long superDomainId,
        String description,
        List<DomainReducedDTO> subdomains
) {
    public DomainDTO(Domain domain) {
        this(
                domain.getId(),
                domain.getSuperdomain() != null ? domain.getSuperdomain().getId() : null,
                domain.getDescription(),
                domain.getSubdomains().stream().map(DomainReducedDTO::new).toList()
        );
    }
}
