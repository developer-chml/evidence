package com.github.developerchml.evdbackend.core.repositories;

import com.github.developerchml.evdbackend.core.entities.proofs.Proof;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProofRepository extends JpaRepository<Proof, Long> {
}
