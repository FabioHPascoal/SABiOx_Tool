package br.com.sabiox.sabiox_tool.model.phases.requirements;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import br.com.sabiox.sabiox_tool.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "requirements")
public class Requirement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;
    
    @OneToOne
    private Priority priority;
    
    @OneToOne
    private Status status;

    private String description;
    
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user; 
    }

    public void setUser(User user) {
        this.user = user; 
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}