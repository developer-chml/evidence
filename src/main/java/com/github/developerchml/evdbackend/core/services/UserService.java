package com.github.developerchml.evdbackend.core.services;

import com.github.developerchml.evdbackend.core.entities.user.User;
import com.github.developerchml.evdbackend.core.entities.user.UserStatus;
import com.github.developerchml.evdbackend.core.mappers.MapperContract;
import com.github.developerchml.evdbackend.core.mappers.UserMapper;
import com.github.developerchml.evdbackend.core.repositories.UserRepository;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestUserDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements CRUDService<RequestUserDTO, ResponseUserDTO, Long> {
    private final UserRepository userRepository;
    private final MapperContract<User, RequestUserDTO, ResponseUserDTO> userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public <User> User find(Long value) {
        return (User) userRepository.findById(value).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @Override
    public ResponseUserDTO findById(Long value) {
        User user = find(value);
        return userMapper.toDTO(user);
    }

    @Override
    public List<ResponseUserDTO> listAll() {
        return userMapper.toListDTO(userRepository.findAll());
    }

    @Override
    public ResponseUserDTO save(RequestUserDTO dto) {
        var user = userMapper.toEntity(dto);
        var newUser = userRepository.save(user);
        return userMapper.toDTO(newUser);
    }

    @Override
    public ResponseUserDTO update(Long value, RequestUserDTO dto) {
        User user = find(value);
        User updateUser = userMapper.updateEntity(user, dto);
        User newUser = userRepository.save(updateUser);
        return userMapper.toDTO(newUser);
    }

    @Override
    public void softDelete(Long value) {
        User user = find(value);
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    public void forceDelete(Long value) {
        userRepository.delete(find(value));
    }
}
