package com.github.developerchml.evdbackend.core.mappers;

import java.util.List;

public interface MapperContract<T, REQ, RES> {

    default T toEntity(REQ dto) {
        return null;
    }

    RES toDTO(T entity);

    T updateEntity(T entity, REQ dto);

    List<RES> toListDTO(List<T> entities);

}
