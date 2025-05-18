package com.github.developerchml.evdbackend.core.services;

import com.github.developerchml.evdbackend.config.FileStorageConfig;
import com.github.developerchml.evdbackend.core.entities.occurrence.Occurrence;
import com.github.developerchml.evdbackend.core.entities.proofs.Proof;
import com.github.developerchml.evdbackend.core.mappers.ProofMapper;
import com.github.developerchml.evdbackend.core.repositories.ProofRepository;
import com.github.developerchml.evdbackend.core.repositories.UserRepository;
import com.github.developerchml.evdbackend.exceptions.FileStorageException;
import com.github.developerchml.evdbackend.exceptions.NotFoundException;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestProofDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseProofDTO;
import com.github.developerchml.evdbackend.storage.FileUpload;
import com.github.developerchml.evdbackend.storage.FileUploadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class ProofService implements CRUDService<RequestProofDTO, ResponseProofDTO, Long> {
    @Autowired
    private FileUploadFactory fileUploadFactory;

    protected final Path fileStorageConfig;

    private final ProofRepository proofRepository;
    private final UserRepository userRepository;
    private final ProofMapper proofMapper;

    public ProofService(ProofRepository proofRepository, UserRepository userRepository, ProofMapper proofMapper, FileStorageConfig fileStorageConfig) {
        this.proofRepository = proofRepository;
        this.userRepository = userRepository;
        this.proofMapper = proofMapper;
        this.fileStorageConfig = Path.of(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
    }

    @Override
    public <Proof> Proof find(Long value) {
        return (Proof) proofRepository.findById(value).orElseThrow(() -> new NotFoundException("Prova(" + value + ") não localizada."));
    }

    @Override
    public ResponseProofDTO findById(Long value) {
        Proof proof = find(value);
        return proofMapper.toDTO(proof);
    }

    @Override
    public ResponseProofDTO update(Long value, RequestProofDTO dto) {
        Proof proof = find(value);

        Proof proofUpdate = proofMapper.updateEntity(proof, dto);

        Proof newProof = proofRepository.save(proofUpdate);

        return proofMapper.toDTO(newProof);
    }

    @Override
    public void softDelete(Long value) {
        Proof proof = find(value);
        proof.setDeleted(true);
        proofRepository.save(proof);
    }

    @Override
    public void forceDelete(Long value) {
        Proof proof = find(value);
        Path deleteFile = this.fileStorageConfig.resolve(proof.getPathFile());
        try {
            FileSystemUtils.deleteRecursively(deleteFile);
        } catch (IOException e) {
            throw new FileStorageException(e.getMessage(), e.getCause());
        }
        proofRepository.delete(proof);
    }

    @Override
    public void recoverSoftDelete(Long value) {
        Proof proof = find(value);
        proof.setDeleted(false);
        proofRepository.save(proof);
    }

    public void save(Occurrence occurrence, MultipartFile file) {
        FileUpload fileUpload = fileUploadFactory.of(file);
        Path pathFile = fileUpload.store(file, occurrence.getId().toString()).getPartialPath();
        //Precisa alterar para buscar o usuario logado
        var loggedUser = userRepository.findById(1L).get();
        var proof = new Proof(occurrence, loggedUser, pathFile.toString());
        proofRepository.save(proof);
    }
}
