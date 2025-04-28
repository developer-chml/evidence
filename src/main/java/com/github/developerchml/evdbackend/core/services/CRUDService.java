package com.github.developerchml.evdbackend.core.services;

import java.util.List;

public interface CRUDService<REQ extends Record, RES extends Record, ID> {

    <T> T find(ID value);

    RES findById(ID value);

    List<RES> listAll();

    RES save(REQ dto);

    RES update(ID value, REQ dto);

    void softDelete(ID value);

    void forceDelete(ID value);
}
