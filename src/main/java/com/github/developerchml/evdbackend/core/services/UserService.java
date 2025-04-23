package com.github.developerchml.evdbackend.core.services;

import com.github.developerchml.evdbackend.core.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
