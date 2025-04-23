package com.github.developerchml.evdbackend.infrastruct.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CRUDController<REQ extends Record, RES extends Record, ID> {

    ResponseEntity<RES> findById(ID value);

    ResponseEntity<List<RES>> listAll();

    ResponseEntity<RES> save(REQ dto);

    ResponseEntity<RES> update(ID value, REQ dto);

    ResponseEntity<Void> softDelete(ID value);

    ResponseEntity<Void> forceDelete(ID value);
}
