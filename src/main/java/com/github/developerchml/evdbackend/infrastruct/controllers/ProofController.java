package com.github.developerchml.evdbackend.infrastruct.controllers;

import com.github.developerchml.evdbackend.core.services.ProofService;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestProofDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseProofDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/proofs")
public class ProofController implements CRUDController<RequestProofDTO, ResponseProofDTO, Long> {
    private final ProofService proofService;

    public ProofController(ProofService proofService) {
        this.proofService = proofService;
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ResponseProofDTO> update(@PathVariable Long id, @RequestBody RequestProofDTO dto) {
        return ResponseEntity.ok(proofService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        proofService.softDelete(id);
        return CRUDController.super.softDelete(id);
    }

    @DeleteMapping("/{id}/force-delete")
    @Override
    public ResponseEntity<Void> forceDelete(@PathVariable Long id) {
        proofService.forceDelete(id);
        return CRUDController.super.forceDelete(id);
    }

    @PatchMapping("/{id}/recover")
    @Override
    public ResponseEntity<Void> recoverSoftDelete(@PathVariable Long id) {
        proofService.recoverSoftDelete(id);
        return CRUDController.super.recoverSoftDelete(id);
    }
}
