package br.com.sabiox.sabiox_tool.domain.sabiox.activity;

import java.time.LocalDate;

import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "activity")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_stage")
    private ActivityStage activityStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type")
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "life_cycle_id")
    private LifeCycle lifeCycle;

    public Activity(ActivityType activityType, LifeCycle lifeCycle) {
        this.activityStage = ActivityStage.NOT_STARTED;
        this.activityType = activityType;
        this.lifeCycle = lifeCycle;
    }
}
