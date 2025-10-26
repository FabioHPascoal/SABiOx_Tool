package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.domain;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifyDomain.IdentifyDomain;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.IdentifySubdomains;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "domain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "identify_subdomains_id")
    private IdentifySubdomains identifySubdomains;

    @OneToOne
    @JoinColumn(name = "identify_domain_id")
    private IdentifyDomain identifyDomain;

    @ManyToOne
    @JoinColumn(name = "super_domain_id")
    private Domain superdomain;

    @OneToMany(mappedBy = "superdomain", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Domain> subdomains = new HashSet<>();
}
