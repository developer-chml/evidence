package com.github.developerchml.evdbackend.core.entities.user;

import com.github.developerchml.evdbackend.core.entities.valueObject.Email;
import com.github.developerchml.evdbackend.core.entities.valueObject.Password;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", unique = true))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private Password password;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.CREATED;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.DEFAULT;

    protected User() {
    }

    public User(String name, Email email, String password) {
        this.name = name;
        this.email = email;
        this.password = Password.of(password);
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
        return password.getValue();
    }

    public void setPassword(String password) {
        this.password = Password.of(password);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email=" + email +
                ", status=" + status +
                ", role=" + role +
                '}';
    }
}
