package com.github.developerchml.evdbackend.core.repositories;

import com.github.developerchml.evdbackend.core.entities.occurrence.Occurrence;
import com.github.developerchml.evdbackend.core.entities.occurrence.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    Optional<Occurrence> findByTitleAndOccurredAtAndOperation(String title, LocalDate occurredAt, Operation operation);
}
