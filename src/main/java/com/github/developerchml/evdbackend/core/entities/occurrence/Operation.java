package com.github.developerchml.evdbackend.core.entities.occurrence;

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
        throw new RuntimeException(str + " is invalid");
    }
}
