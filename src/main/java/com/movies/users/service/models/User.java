package com.movies.users.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "user_model")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
    private UUID id;
    private String username;
    private String name;
    private String surname;
    @JsonIgnore
    private String password;
    private LocalDate birthdate;

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
