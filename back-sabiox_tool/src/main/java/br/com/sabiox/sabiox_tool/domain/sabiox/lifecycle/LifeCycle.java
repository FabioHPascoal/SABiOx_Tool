package br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle;

import java.time.LocalDate;

import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "life_cycles")
public class LifeCycle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDate startDate;

    @CreationTimestamp
    private LocalDate endDate;

    @ManyToOne
    private Phase phase;
    
    public Long getId() {return id;}

    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}

    public Phase getPhase() {return phase;}
    public void setPhase(Phase phase) {this.phase = phase;}

}
