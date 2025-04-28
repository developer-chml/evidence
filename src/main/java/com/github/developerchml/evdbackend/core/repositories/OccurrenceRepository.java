package com.github.developerchml.evdbackend.core.repositories;

import com.github.developerchml.evdbackend.core.entities.occurrence.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {
}
