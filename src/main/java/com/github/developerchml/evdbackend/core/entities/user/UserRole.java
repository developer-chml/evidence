package com.github.developerchml.evdbackend.core.entities.user;

import com.github.developerchml.evdbackend.exceptions.NotFoundException;

public enum UserRole {
    DEFAULT,
    GUEST,
    ADMIN;

    public static UserRole toEnum(String str) {
        if (str == null) {
            return null;
        }

        for (UserRole s : UserRole.values()) {
            if (str.equalsIgnoreCase(s.name())) {
                return s;
            }
        }
        throw new NotFoundException(str + " não localizado.");
    }
}
