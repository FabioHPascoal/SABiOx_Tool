package br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.sabiox.sabiox_tool.domain.sabiox.activity.Activity;
import br.com.sabiox.sabiox_tool.domain.sabiox.activity.ActivityType;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Table(name = "life_cycle")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LifeCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    private Phase phase;

    @OneToMany(mappedBy = "lifeCycle", orphanRemoval = true)
    @MapKey(name = "activityType")
    private Map<ActivityType, Activity> activities = new HashMap<>();
}
