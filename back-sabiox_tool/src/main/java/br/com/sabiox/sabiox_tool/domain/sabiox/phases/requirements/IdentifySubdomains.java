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

import java.util.ArrayList;
import java.util.List;

@Table(name = "identify_subdomains")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdentifySubdomains extends Activity {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Domain> subDomains = new ArrayList<>();

    public IdentifySubdomains(ActivityType activityType,
                         LifeCycle lifeCycle) {
        super(activityType, lifeCycle);
    }
}
