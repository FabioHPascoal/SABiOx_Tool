package br.com.sabiox.sabiox_tool.model.phases.requirements;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "domains")
public class Domain {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    
    @OneToOne // change later
    private Domain subDomain;

    @ManyToOne
    private RequirementActivity requirementActivity;
    
    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Domain getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(Domain subDomain) {
        this.subDomain = subDomain;
    }

    public RequirementActivity getRequirementActivity() {
        return requirementActivity;
    }

    public void setRequirementActivity(RequirementActivity requirementActivity) {
        this.requirementActivity = requirementActivity;
    }

}