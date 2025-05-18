package com.github.developerchml.evdbackend.core.services;

import com.github.developerchml.evdbackend.config.FileStorageConfig;
import com.github.developerchml.evdbackend.core.entities.occurrence.Occurrence;
import com.github.developerchml.evdbackend.core.entities.occurrence.Operation;
import com.github.developerchml.evdbackend.core.entities.proofs.Proof;
import com.github.developerchml.evdbackend.core.mappers.MapperContract;
import com.github.developerchml.evdbackend.core.mappers.OccurrenceMapper;
import com.github.developerchml.evdbackend.core.repositories.OccurrenceRepository;
import com.github.developerchml.evdbackend.core.repositories.UserRepository;
import com.github.developerchml.evdbackend.exceptions.FileNotAcceptedException;
import com.github.developerchml.evdbackend.exceptions.FileStorageException;
import com.github.developerchml.evdbackend.exceptions.NotFoundException;
import com.github.developerchml.evdbackend.exceptions.ValidateUniqueException;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestOccurrenceDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseOccurrenceDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OccurrenceService implements CRUDService<RequestOccurrenceDTO, ResponseOccurrenceDTO, Long> {

    private final OccurrenceRepository occurrenceRepository;
    private final MapperContract<Occurrence, RequestOccurrenceDTO, ResponseOccurrenceDTO> occurrenceMapper;
    private final UserRepository userRepository;
    private final ProofService proofService;

    protected final Path fileStorageConfig;

    public OccurrenceService(OccurrenceRepository occurrenceRepository, OccurrenceMapper occurrenceMapper, UserRepository userRepository, ProofService proofService , FileStorageConfig fileStorageConfig) {
        this.occurrenceRepository = occurrenceRepository;
        this.occurrenceMapper = occurrenceMapper;
        this.userRepository = userRepository;
        this.proofService = proofService;

        this.fileStorageConfig = Path.of(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
    }

    @Override
    public <Occurrence> Occurrence find(Long value) {
        return (Occurrence) occurrenceRepository.findById(value).orElseThrow(() -> new NotFoundException("Ocorrência(" + value + ") não localizada."));
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
        validateUnique(dto,null);
        var occurrence = occurrenceMapper.toEntity(dto);
                         //Precisa alterar para buscar o usuario logado
        var loggedUser = userRepository.findById(1L).get();
        occurrence.setOwner(loggedUser);
        var newOccurrence = occurrenceRepository.save(occurrence);
        return occurrenceMapper.toDTO(newOccurrence);
    }

    @Override
    public ResponseOccurrenceDTO update(Long value, RequestOccurrenceDTO dto) {
        validateUnique(dto,value);
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
        Occurrence occurrence = find(value);
        Path deleteFile = this.fileStorageConfig.resolve(occurrence.getId().toString());
        try {
            FileSystemUtils.deleteRecursively(deleteFile);
        } catch (IOException e) {
            throw new FileStorageException(e.getMessage(), e.getCause());
        }
        occurrenceRepository.delete(occurrence);
    }

    public void saveProof(Long value, MultipartFile file) {
        Occurrence occurrence = find(value);
        proofService.save(occurrence,file);
    }

    public void saveProofs(Long value, MultipartFile[] files) {
       List<String> errors = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                saveProof(value, file);
            } catch (FileNotAcceptedException e) {
                errors.add(e.getMessage());
            }
        }
        if (!errors.isEmpty())
            throw new FileNotAcceptedException("Arquivos não aceitos", errors);
    }

    @Transactional
    public List<Proof> proofs(Long value) {
        Occurrence occurrence = find(value);
        return occurrence.getProofs();
    }

    public void recoverSoftDelete(Long value) {
        Occurrence occurrence = find(value);
        occurrence.setSoftDelete(null);
        occurrenceRepository.save(occurrence);
    }

    private void validateUnique(RequestOccurrenceDTO dto, Long value){
        var occurrence = occurrenceRepository.findByTitleAndOccurredAtAndOperation(dto.title(), LocalDate.parse(dto.occurredAt()), Operation.toEnum(dto.operation()));

        if (occurrence.isPresent() && occurrence.get().getSoftDelete() == null && !Objects.equals(occurrence.get().getId(), value)) {
            throw new ValidateUniqueException("Ocorrência já foi registrada por " + occurrence.get().getOwner() + " em " + occurrence.get().getOccurredAt());
        }

        if (occurrence.isPresent() && occurrence.get().getSoftDelete() != null) {
            this.recoverSoftDelete(occurrence.get().getId());
        }
    }


}
