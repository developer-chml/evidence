package com.github.developerchml.evdbackend.core.entities.valueObject;

import jakarta.persistence.Embeddable;
import org.springframework.util.StringUtils;

@Embeddable
public class Password {
    protected String value;

    protected Password() {
    }

    protected Password(String value) {
        if (!StringUtils.hasText(value)) {
            throw new RuntimeException("password is invalid");
        }
        this.value = this.encrypt(value);
    }

    public static Password of(String value) {
        return new Password(value);
    }

    public String getValue() {
        return this.value;
    }

    private String encrypt(String value) {
        return value;
    }

    public Password validatedPasswordConfirmation(String passwordConfirmation) {
        if (!this.value.equalsIgnoreCase(passwordConfirmation)) {
            throw new RuntimeException("Password Confirmation is invalid");
        }
        return this;
    }
}
