package com.github.developerchml.evdbackend.core.entities.occurrence;

import com.github.developerchml.evdbackend.core.entities.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "occurrences")
public class Occurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate occurredAt;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    @ManyToOne
    private User owner;

    private LocalDateTime softDelete;

    protected Occurrence() {
    }

    public Occurrence(String title, String description, LocalDate occurredAt, Operation operation) {
        this.title = title;
        this.description = description;
        this.occurredAt = occurredAt;
        this.operation = operation;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(LocalDate occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getOperation() {
        return operation.name();
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getOwner() {
        return owner.getName();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDateTime getSoftDelete() {
        return softDelete;
    }

    public void setSoftDelete(LocalDateTime softDelete) {
        this.softDelete = softDelete;
    }
}
