package com.github.developerchml.evdbackend.core.services;

import java.util.List;

public interface CRUDService<REQ extends Record, RES extends Record, ID> {

    <T> T find(ID value);

    default RES findById(ID value) {
        return null;
    }

    default List<RES> listAll() {
        return null;
    }

    default RES save(REQ dto) {
        return null;
    }

    default RES update(ID value, REQ dto) {
        return null;
    }

    default void softDelete(ID value) {

    }

    default void forceDelete(ID value) {

    }

    default void recoverSoftDelete(ID value) {

    }
}
