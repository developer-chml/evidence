package com.github.developerchml.evdbackend.core.mappers;

import com.github.developerchml.evdbackend.core.entities.proofs.Proof;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestProofDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseProofDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProofMapper implements MapperContract<Proof, RequestProofDTO, ResponseProofDTO>{

    @Override
    public ResponseProofDTO toDTO(Proof entity) {
        return new ResponseProofDTO(entity.getId(), entity.getPathFile(), entity.getDescription());
    }

    @Override
    public Proof updateEntity(Proof entity, RequestProofDTO dto) {
        if(Objects.nonNull(dto.description())){
            entity.setDescription(dto.description());
        }
        return entity;
    }

    @Override
    public List<ResponseProofDTO> toListDTO(List<Proof> entities) {
        return entities.stream().map(e -> new ResponseProofDTO(e.getId(), e.getPathFile(), e.getDescription())).toList();
    }
}
