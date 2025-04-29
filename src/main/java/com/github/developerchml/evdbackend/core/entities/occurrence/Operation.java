package com.github.developerchml.evdbackend.core.entities.occurrence;

import com.github.developerchml.evdbackend.exceptions.NotFoundException;

public enum Operation {
    IN,
    OUT,
    OTHERS;

    public static Operation toEnum(String str) {
        if (str == null) {
            return null;
        }

        for (Operation s : Operation.values()) {
            if (str.equalsIgnoreCase(s.name())) {
                return s;
            }
        }
        throw new NotFoundException(str + " não localizado.");
    }
}
