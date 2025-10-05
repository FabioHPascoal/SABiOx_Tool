package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "define_purpose")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefinePurpose extends Activity {
    @Column(name = "what_question")
    private String whatQuestion;

    @Column(name = "for_what_question")
    private String forWhatQuestion;

    @Column(name = "why_question")
    private String whyQuestion;

    public DefinePurpose(ActivityType activityType, LifeCycle lifeCycle) {
        super(activityType, lifeCycle);
    }
}
