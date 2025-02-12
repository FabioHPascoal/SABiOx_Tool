package br.com.sabiox.sabiox_tool.model.phases;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @CreationTimestamp
    private LocalDate startDate;
    
    @CreationTimestamp
    private LocalDate endDate;
    
    LifeCycleStage lifeCycleStage;

    @ManyToOne
    LifeCycle lifeCycle;

    @ManyToOne
    Phase phase;
    
    public Long getId() {return id;}
    
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    
    public LifeCycleStage getLifeCycleStage() {return lifeCycleStage;}
    public void setLifeCycleStage(LifeCycleStage lifeCycleStage) {
        this.lifeCycleStage = lifeCycleStage;
    }
    
    public LifeCycle getLifeCycle() {return lifeCycle;}
    public void setLifeCycle(LifeCycle lifeCycle) {this.lifeCycle = lifeCycle;}
    
    public Phase getPhase() {return phase;}
    public void setPhase(Phase phase) {this.phase = phase;}
}