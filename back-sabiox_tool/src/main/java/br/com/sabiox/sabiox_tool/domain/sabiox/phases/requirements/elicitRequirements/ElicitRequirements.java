package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements.requirement.Requirement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "elicit_requirements")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElicitRequirements extends Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "elicitRequirements", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Requirement> requirements = new ArrayList<>();

    public ElicitRequirements(ActivityType activityType, LifeCycle lifeCycle) {
        super(activityType, lifeCycle);
    }
}
