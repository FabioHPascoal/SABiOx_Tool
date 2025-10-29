package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.subdomain;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.IdentifySubdomains;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subdomain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subdomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "identify_subdomains_id")
    private IdentifySubdomains identifySubdomains;
}
