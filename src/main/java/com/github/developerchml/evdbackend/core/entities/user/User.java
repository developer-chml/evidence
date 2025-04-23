package com.github.developerchml.evdbackend.core.entities.user;

import com.github.developerchml.evdbackend.core.entities.valueObject.Email;

public class User {

    private Long id;

    private String name;

    private Email email;

    private String password;

    private UserStatus status = UserStatus.CREATED;

    private UserRole role = UserRole.DEFAULT;

    public User() {
    }

    public User(String name, Email email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email.toString();
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getRole() {
        return role.name();
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
