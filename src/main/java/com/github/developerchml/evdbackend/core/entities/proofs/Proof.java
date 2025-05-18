package com.github.developerchml.evdbackend.core.entities.proofs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.developerchml.evdbackend.core.entities.occurrence.Occurrence;
import com.github.developerchml.evdbackend.core.entities.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "proofs")
public class Proof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pathFile;

    private String description;

    @ManyToOne
    @JsonBackReference
    private Occurrence occurrence;

    @ManyToOne
    @JsonBackReference
    private User owner;

    private boolean deleted;

    public Proof() {
    }

    public Proof(Occurrence occurrence, User owner, String pathFile) {
        this.occurrence = occurrence;
        this.owner = owner;
        this.pathFile = pathFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
    }

    public String getOwner() {
        return owner.getName();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Proof proofs = (Proof) o;
        return Objects.equals(id, proofs.id) && Objects.equals(pathFile, proofs.pathFile) && Objects.equals(description, proofs.description) && Objects.equals(occurrence, proofs.occurrence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pathFile, description, occurrence);
    }

    @Override
    public String toString() {
        return "Proof{" +
                "id=" + id +
                ", pathFile='" + pathFile + '\'' +
                ", description='" + description + '\'' +
                ", occurrence=" + occurrence +
                ", owner=" + owner.getName() +
                ", deleted=" + deleted +
                '}';
    }
}
