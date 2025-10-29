package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.domain.DomainDTO;

public record IdentifyDomainResponseDTO(
        Long id,
        DomainDTO domainDTO
) {
    public IdentifyDomainResponseDTO(IdentifyDomain identifyDomain) {
        this(
                identifyDomain.getId(),
                new DomainDTO(identifyDomain.getDomain())
        );
    }
}