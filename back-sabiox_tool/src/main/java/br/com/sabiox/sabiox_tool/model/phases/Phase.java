package br.com.sabiox.sabiox_tool.model.phases;

import br.com.sabiox.sabiox_tool.model.Project;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "phases")
public class Phase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Project project;
    
    private PhaseType phaseType;
    
    public Long getId() {return id;}

    public Project getProject() {return project;}
    public void setProject(Project project) {this.project = project;}

    public PhaseType getPhaseType() {return phaseType;}
    public void setPhaseType(PhaseType phaseType) {this.phaseType = phaseType;}
}