package br.com.sabiox.sabiox_tool.domain.sabiox.phases.requirements;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

// @Entity
// @Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RequirementActivity activity;

    private String description;

    public Long getId() {
        return id;
    }

    public RequirementActivity getActivity() {
        return activity;
    }

    public void setActivity(RequirementActivity activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}