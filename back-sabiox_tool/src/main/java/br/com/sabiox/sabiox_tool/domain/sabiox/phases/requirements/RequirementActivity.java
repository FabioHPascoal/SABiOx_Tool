package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements;

import br.com.sabiox.sabiox_tool.domain.sabiox.Activity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "requirement_activities")
public class RequirementActivity extends Activity {
    private RequirementActivityType activityType;
    private String purpose;
    
    public RequirementActivityType getActivityType() {
        return activityType;
    }
    public void setActivityType(RequirementActivityType activityType) {
        this.activityType = activityType;
    }

    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}