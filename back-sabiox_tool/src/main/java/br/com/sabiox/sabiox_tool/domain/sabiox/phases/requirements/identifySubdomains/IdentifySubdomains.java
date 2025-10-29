package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.identifySubdomains.subdomain.Subdomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "identify_subdomains")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdentifySubdomains extends Activity {
    @OneToMany(mappedBy = "identifySubdomains", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subdomain> subdomains = new ArrayList<>();

    public IdentifySubdomains(ActivityType activityType, LifeCycle lifeCycle) {
        super(activityType, lifeCycle);
    }
}
