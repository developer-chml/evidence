package com.github.developerchml.evdbackend.core.mappers;

import com.github.developerchml.evdbackend.core.entities.occurrence.Occurrence;
import com.github.developerchml.evdbackend.core.entities.occurrence.Operation;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestOccurrenceDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseOccurrenceDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class OccurrenceMapper implements MapperContract<Occurrence, RequestOccurrenceDTO, ResponseOccurrenceDTO> {
    @Override
    public Occurrence toEntity(RequestOccurrenceDTO dto) {
        return new Occurrence(dto.title(), dto.description(), LocalDate.parse(dto.occurredAt()), Operation.toEnum(dto.operation()));
    }

    @Override
    public ResponseOccurrenceDTO toDTO(Occurrence entity) {
        return new ResponseOccurrenceDTO(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getOccurredAt(), entity.getOperation(), entity.getOwner(), entity.getSoftDelete());
    }

    @Override
    public Occurrence updateEntity(Occurrence entity, RequestOccurrenceDTO dto) {
        if (Objects.nonNull(dto.title())){
            entity.setTitle(dto.title());
        }
        if (Objects.nonNull(dto.occurredAt())){
            entity.setOccurredAt(LocalDate.parse(dto.occurredAt()));
        }
        if (Objects.nonNull(dto.operation())){
            entity.setOperation(Operation.toEnum(dto.operation()));
        }
        return entity;
    }

    @Override
    public List<ResponseOccurrenceDTO> toListDTO(List<Occurrence> entities) {
        return entities.stream().map(e -> new ResponseOccurrenceDTO(e.getId(), e.getTitle(), e.getDescription(), e.getOccurredAt(), e.getOperation(), e.getOwner(), e.getSoftDelete())).toList();
    }
}
