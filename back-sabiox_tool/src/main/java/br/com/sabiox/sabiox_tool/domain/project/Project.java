package br.com.sabiox.sabiox_tool.domain.project;

import java.time.LocalDate;
import java.util.*;

import br.com.sabiox.sabiox_tool.domain.ProjectUser.ParticipationType;
import br.com.sabiox.sabiox_tool.domain.ProjectUser.ProjectUser;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;
import br.com.sabiox.sabiox_tool.domain.sabiox.phase.PhaseType;
import br.com.sabiox.sabiox_tool.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Table(name = "project")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Boolean isEnabled;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectUser> participants = new ArrayList<>();

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    @MapKey(name = "phaseType")
    private Map<PhaseType, Phase> phases = new HashMap<>(5);
    
    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;

    @Column(name = "conclusion_date")
    private LocalDate conclusionDate;

    public User getOwner() {
        return this.getParticipants().stream()
                .filter(pu -> pu.getParticipationType() == ParticipationType.OWNER)
                .findFirst()
                .map(ProjectUser::getUser)
                .orElseThrow(() -> new IllegalStateException("Project has no owner"));
    }

    public boolean isOwner(User user) {
        return this.getOwner().getId().equals(user.getId());
    }

    public boolean hasUser(User user) {
        return this.getParticipants().stream()
                .anyMatch(pu -> Objects.equals(pu.getUser().getId(), user.getId()));
    }
}
