package com.github.developerchml.evdbackend.infrastruct.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseProofsDTO(Long id, String title, String description, LocalDate occurredAt, String operation, String owner, LocalDateTime softDelete, List<ResponseProofDTO> files) {
}
