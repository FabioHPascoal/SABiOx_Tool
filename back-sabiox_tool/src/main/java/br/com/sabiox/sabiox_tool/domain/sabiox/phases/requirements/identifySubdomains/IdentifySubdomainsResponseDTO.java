package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.subdomain.SubdomainDTO;

import java.util.List;

public record IdentifySubdomainsResponseDTO(
        Long id,
        List<SubdomainDTO> subdomains
) {
    public IdentifySubdomainsResponseDTO(IdentifySubdomains identifySubdomains) {
        this(
                identifySubdomains.getId(),
                identifySubdomains.getSubdomains().stream().map(SubdomainDTO::new).toList()
        );
    }
}