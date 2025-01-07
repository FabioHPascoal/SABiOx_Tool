package br.com.sabiox.sabiox_tool.model.phases.requirements;

import br.com.sabiox.sabiox_tool.model.phases.Activity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "requirement_activities")
public class RequirementActivity extends Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private RequirementActivityType activityType;
    
    public Long getId() {
        return id;
    }

    public RequirementActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(RequirementActivityType activityType) {
        this.activityType = activityType;
    }

}