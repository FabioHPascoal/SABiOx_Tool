package br.com.sabiox.sabiox_tool.domain.sabiox.phase;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "phases")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private PhaseType phaseType;

    public Phase(Project project, PhaseType phaseType) {
        this.project = project;
        this.phaseType = phaseType;
    }
}
