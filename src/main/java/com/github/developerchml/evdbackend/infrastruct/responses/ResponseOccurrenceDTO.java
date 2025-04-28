package com.github.developerchml.evdbackend.infrastruct.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ResponseOccurrenceDTO(Long id, String title, String description, LocalDate occurredAt, String operation, String owner, LocalDateTime softDelete) {
}
