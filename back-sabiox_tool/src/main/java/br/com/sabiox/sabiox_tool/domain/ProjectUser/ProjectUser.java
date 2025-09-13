package br.com.sabiox.sabiox_tool.domain.ProjectUser;

import br.com.sabiox.sabiox_tool.domain.project.Project;
import br.com.sabiox.sabiox_tool.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "project_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    @CreationTimestamp
    @Column(name = "participation_start_date", nullable = false, updatable = false)
    private LocalDate participationStart;

    @Column(name = "participation_end_date")
    private LocalDate participationEnd;

    @Enumerated(EnumType.STRING)
    private ParticipationType participationType;

    @Email
    private String email;
}
