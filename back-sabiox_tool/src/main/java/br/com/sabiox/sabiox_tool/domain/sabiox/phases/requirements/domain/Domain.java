package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.domain;

import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.IdentifyDomain;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.IdentifySubdomains;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "domain")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
}
