package com.github.developerchml.evdbackend.core.entities.valueObject;

import jakarta.persistence.Embeddable;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email {
    protected String value;

    protected Email() {
    }

    protected Email(String value) {
        if (!validate(value)) {
            throw new RuntimeException("email is invalid");
        }
        this.value = value;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    private boolean validate(String value) {
        return StringUtils.hasText(value) && Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(value, email1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}