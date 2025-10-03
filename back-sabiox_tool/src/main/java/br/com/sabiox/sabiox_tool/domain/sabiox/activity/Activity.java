package br.com.sabiox.sabiox_tool.domain.sabiox.activity;

import java.time.LocalDate;

import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_stage")
    private ActivityStage activityStage;

    @ManyToOne
    @JoinColumn(name = "life_cycle_id")
    private LifeCycle lifeCycle;
}
