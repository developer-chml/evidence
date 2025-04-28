package com.github.developerchml.evdbackend.infrastruct.requests;

public record RequestUserCredentialDTO(String currentPassword, String password, String passwordConfirmation) {
}
