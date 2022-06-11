package com.movies.users.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.UUID;

public record User(
        UUID id,
        String username,
        String name,
        String surname,
        @JsonIgnore String password,
        LocalDate birthdate
) {

    public boolean equalsByNonNullFields(User other) {
        if (other == null) return false;
        var idEq = switch (other.id) {
            case null -> true;
            case UUID id -> this.id.equals(id);
        };
        var usernameEq = switch (other.username) {
            case null -> true;
            case String username -> this.username.equals(username);
        };
        var nameEq = switch (other.name) {
            case null -> true;
            case String name -> this.name.equals(name);
        };
        var surnameEq = switch (other.surname) {
            case null -> true;
            case String surname -> this.surname.equals(surname);
        };
        var birthdateEq = switch (other.birthdate) {
            case null -> true;
            case LocalDate birthdate -> this.birthdate.equals(birthdate);
        };
        return idEq && usernameEq && nameEq && surnameEq && birthdateEq;
    }
}
