package com.github.developerchml.evdbackend.infrastruct.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CRUDController<REQ extends Record, RES extends Record, ID> {

    default ResponseEntity<RES> findById(ID value) {
        return null;
    }

    default ResponseEntity<List<RES>> listAll() {
        return null;
    }

    default ResponseEntity<RES> save(REQ dto) {
        return null;
    }

    default ResponseEntity<RES> update(ID value, REQ dto) {
        return null;
    }

    default ResponseEntity<Void> softDelete(ID value) {
        return ResponseEntity.noContent().build();
    }

    default ResponseEntity<Void> recoverSoftDelete(ID value) {
        return ResponseEntity.noContent().build();
    }

    default ResponseEntity<Void> forceDelete(ID value) {
        return ResponseEntity.noContent().build();
    }
}
