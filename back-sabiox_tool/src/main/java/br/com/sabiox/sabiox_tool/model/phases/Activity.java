package br.com.sabiox.sabiox_tool.model.phases;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Activity {
    
    LifeCycleStage lifeCycleStage;
    
    @CreationTimestamp
    private LocalDate startDate;

    @CreationTimestamp
    private LocalDate endDate;

    public LifeCycleStage getLifeCycleStage() {return lifeCycleStage;}
    public void setLifeCycleStage(LifeCycleStage lifeCycleStage) {
        this.lifeCycleStage = lifeCycleStage;
    }

    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}

}
