package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.subdomain;

public record SubdomainDTO(
        Long id,
        String title,
        String description
) {
    public SubdomainDTO(Subdomain subdomain) {
        this(
                subdomain.getId(),
                subdomain.getTitle(),
                subdomain.getDescription()
        );
    }
}
