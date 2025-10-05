package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.domain.Domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "identify_domain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdentifyDomain extends Activity {
    @JoinColumn(name = "domain_id")
    @OneToOne(mappedBy = "identifyDomain", cascade = CascadeType.ALL, orphanRemoval = true)
    private Domain domain;

    public IdentifyDomain(ActivityType activityType, LifeCycle lifeCycle) {
        super(activityType, lifeCycle);
    }
}
