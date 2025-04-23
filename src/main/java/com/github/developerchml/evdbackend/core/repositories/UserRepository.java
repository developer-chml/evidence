package com.github.developerchml.evdbackend.core.repositories;

import com.github.developerchml.evdbackend.core.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
