package com.github.developerchml.evdbackend.core.services;

import com.github.developerchml.evdbackend.core.entities.user.User;

import com.github.developerchml.evdbackend.core.entities.user.UserRole;
import com.github.developerchml.evdbackend.core.entities.user.UserStatus;
import com.github.developerchml.evdbackend.core.entities.valueObject.Email;
import com.github.developerchml.evdbackend.core.entities.valueObject.Password;
import com.github.developerchml.evdbackend.core.mappers.MapperContract;
import com.github.developerchml.evdbackend.core.mappers.UserMapper;
import com.github.developerchml.evdbackend.core.repositories.UserRepository;

import com.github.developerchml.evdbackend.exceptions.NotAdminException;
import com.github.developerchml.evdbackend.exceptions.NotFoundException;
import com.github.developerchml.evdbackend.exceptions.ValidateUniqueException;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestUserCredentialDTO;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestUserDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return (User) userRepository.findById(value).orElseThrow(() -> new NotFoundException("Usuário(" + value + ") não localizado."));
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
        validateEmailUnique(user.getEmail(), null);
        var newUser = userRepository.save(user);
        return userMapper.toDTO(newUser);
    }

    @Override
    public ResponseUserDTO update(Long value, RequestUserDTO dto) {
        User user = find(value);
        User updateUser = userMapper.updateEntity(user, dto);
        validateEmailUnique(updateUser.getEmail(), user.getId());
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

    public void activate(Long value) {
        User user = find(value);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public void block(Long value) {
        User user = find(value);
        user.setStatus(UserStatus.BLOCKED);
        userRepository.save(user);
    }

    public void changeCredential(Long value, RequestUserCredentialDTO credentialDTO) {
        User user = find(value);
      
        if (user.getStatus().equals(UserStatus.DELETED.name())) {
            throw new NotFoundException("Usuário(" + value + ") está deletado.");
        }

        Password newPassword = Password.of(credentialDTO.password()).validatedPasswordConfirmation(credentialDTO.passwordConfirmation());
        if (!user.getPassword().equals(Password.of(credentialDTO.currentPassword()).getValue())) {
            throw new RuntimeException("Credênciais invalidas, tente novamente.");
        }
        user.setPassword(newPassword.getValue());
        userRepository.save(user);
    }

    private void validateEmailUnique(String email, Long id) {
        var user = userRepository.findByEmail(Email.of(email));

        if (user.isPresent() && !Objects.equals(user.get().getId(), id)) {
            throw new ValidateUniqueException(email + " não está disponível.");
        }
    }
}
