package com.github.developerchml.evdbackend.core.mappers;

import com.github.developerchml.evdbackend.core.entities.user.User;
import com.github.developerchml.evdbackend.core.entities.valueObject.Email;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestUserDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseUserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserMapper implements MapperContract<User, RequestUserDTO, ResponseUserDTO> {
    @Override
    public User toEntity(RequestUserDTO dto) {
        return new User(dto.name(), Email.of(dto.email()), dto.password());
    }

    @Override
    public ResponseUserDTO toDTO(User entity) {
        return new ResponseUserDTO(entity.getId().toString(), entity.getName(), entity.getEmail(), entity.getStatus(), entity.getRole());
    }

    @Override
    public List<ResponseUserDTO> toListDTO(List<User> entities) {
        return entities.stream().map(e -> new ResponseUserDTO(e.getId().toString(), e.getName(), e.getEmail(), e.getStatus(), e.getRole())).toList();
    }

    @Override
    public User updateEntity(User entity, RequestUserDTO dto) {
        if (Objects.nonNull(dto.name())) {
            entity.setName(dto.name());
        }
        if (Objects.nonNull(dto.email())) {
            entity.setEmail(Email.of(dto.email()));
        }
        return entity;
    }
}
