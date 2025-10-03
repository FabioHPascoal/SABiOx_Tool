package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
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
}

