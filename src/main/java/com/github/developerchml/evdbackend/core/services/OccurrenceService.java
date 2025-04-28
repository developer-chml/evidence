package com.github.developerchml.evdbackend.core.services;

import com.github.developerchml.evdbackend.core.entities.occurrence.Occurrence;
import com.github.developerchml.evdbackend.core.mappers.MapperContract;
import com.github.developerchml.evdbackend.core.mappers.OccurrenceMapper;
import com.github.developerchml.evdbackend.core.repositories.OccurrenceRepository;
import com.github.developerchml.evdbackend.core.repositories.UserRepository;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestOccurrenceDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseOccurrenceDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OccurrenceService implements CRUDService<RequestOccurrenceDTO, ResponseOccurrenceDTO, Long> {

    private final OccurrenceRepository occurrenceRepository;
    private final MapperContract<Occurrence, RequestOccurrenceDTO, ResponseOccurrenceDTO> occurrenceMapper;
    private final UserRepository userRepository;

    public OccurrenceService(OccurrenceRepository occurrenceRepository, OccurrenceMapper occurrenceMapper, UserRepository userRepository) {
        this.occurrenceRepository = occurrenceRepository;
        this.occurrenceMapper = occurrenceMapper;
        this.userRepository = userRepository;
    }

    @Override
    public <Occurrence> Occurrence find(Long value) {
        return (Occurrence) occurrenceRepository.findById(value).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @Override
    public ResponseOccurrenceDTO findById(Long value) {
        Occurrence occurrence = find(value);
        return occurrenceMapper.toDTO(occurrence);
    }

    @Override
    public List<ResponseOccurrenceDTO> listAll() {
        return occurrenceMapper.toListDTO(occurrenceRepository.findAll());
    }

    @Override
    public ResponseOccurrenceDTO save(RequestOccurrenceDTO dto) {
        var occurrence = occurrenceMapper.toEntity(dto);
                         //Precisa alterar para buscar o usuario logado
        var loggedUser = userRepository.findById(1L).get();
        occurrence.setOwner(loggedUser);
        var newOccurrence = occurrenceRepository.save(occurrence);
        return occurrenceMapper.toDTO(newOccurrence);
    }

    @Override
    public ResponseOccurrenceDTO update(Long value, RequestOccurrenceDTO dto) {
        Occurrence occurrence = find(value);
        Occurrence updateOccurrence = occurrenceMapper.updateEntity(occurrence, dto);

        Occurrence newOccurrence = occurrenceRepository.save(updateOccurrence);
        return occurrenceMapper.toDTO(newOccurrence);
    }

    @Override
    public void softDelete(Long value) {
        Occurrence occurrence = find(value);
        occurrence.setSoftDelete(LocalDateTime.now());
        occurrenceRepository.save(occurrence);
    }

    @Override
    public void forceDelete(Long value) {
        occurrenceRepository.delete(find(value));
    }
}
