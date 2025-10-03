package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.domain.Domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "identify_domain")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdentifyDomain extends Activity {
    @OneToOne
    @JoinColumn(name = "domain_id")
    Domain domain;
}
