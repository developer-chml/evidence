package com.github.developerchml.evdbackend.core.repositories;

import com.github.developerchml.evdbackend.core.entities.user.User;
import com.github.developerchml.evdbackend.core.entities.valueObject.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(Email email);

}
