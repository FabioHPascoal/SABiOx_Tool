package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements.elicitRequirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "elicit_requirements")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElicitRequirements extends Activity {
    @Column(name = "what_question")
    private String whatQuestion;

    public ElicitRequirements(ActivityType activityType, LifeCycle lifeCycle) {
        super(activityType, lifeCycle);
    }
}

