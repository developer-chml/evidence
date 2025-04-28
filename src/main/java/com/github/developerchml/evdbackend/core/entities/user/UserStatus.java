package com.github.developerchml.evdbackend.core.entities.user;

public enum UserStatus {
    ACTIVE,
    CREATED,
    DELETED,
    BLOCKED;

    public static UserStatus toEnum(String str) {
        if (str == null) {
            return null;
        }

        for (UserStatus s : UserStatus.values()) {
            if (str.equalsIgnoreCase(s.name())) {
                return s;
            }
        }
        throw new RuntimeException(str + " is invalid");
    }
}
