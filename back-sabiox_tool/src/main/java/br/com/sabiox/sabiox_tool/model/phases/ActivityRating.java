package br.com.sabiox.sabiox_tool.model.phases;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ActivityRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private ActivityRatingType activityRatingType;
    
    public Long getId() {return id;}
    
    public ActivityRatingType getActivityRatingType() {
        return activityRatingType;
    }
    public void setActivityRatingType(ActivityRatingType activityRatingType) {
        this.activityRatingType = activityRatingType;
    }
}
