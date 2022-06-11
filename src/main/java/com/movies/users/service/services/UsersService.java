package com.movies.users.service.services;

import com.movies.users.service.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    private final List<User> postgresql = List.of(
            new User(UUID.randomUUID(), "username1", "Antonio", "Banderos", "some hash", LocalDate.of(1980, 10, 10)),
            new User(UUID.randomUUID(), "username2", "Lex", "Musk", "some hash", LocalDate.of(1981, 10, 10)),
            new User(UUID.randomUUID(), "username3", "Joe", "Smith", "some hash", LocalDate.of(2000, 10, 10)),
            new User(UUID.randomUUID(), "username4", "Jay", "Brown", "some hash", LocalDate.of(2015, 10, 10)),
            new User(UUID.randomUUID(), "username5", "Alex", "Jones", "some hash", LocalDate.of(2010, 10, 10))
    );

    public UUID createUser(User u) {
        validate(u);
        return UUID.randomUUID();
    }

    public User deleteUser(UUID id) {
        return findById(id);
    }

    public void updateUser(User u) {
        validate(u);
        findById(u.id());
    }

    public User findUser(User fields) {
        return findByFields(fields);
    }

    public List<User> getAll() {
        return postgresql;
    }

    private User findById(UUID id) {
        return postgresql.stream()
                .filter(u -> u.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    private User findByFields(User u) {
        var users = postgresql.stream()
                .filter(usr -> usr.equalsByNonNullFields(u))
                .toList();
        return switch (users.size()) {
            case 1 -> users.get(0);
            case 0 -> throw new RuntimeException("user not found");
            default -> throw new RuntimeException("found more than 1 users");
        };
    }

    private void validate(User u) {
        if (u == null) throw new RuntimeException("user is null");
        var onlyAlphabeticPattern = "^[a-zA-Z]*$";
        var errors = new ArrayList<String>();
        if (u.id() == null) errors.add("user id is null");
        switch (u.username()) {
            case null -> errors.add("username is null");
            case "" -> errors.add("username is blank");
            case String username && username.length() < 5 -> errors.add("username must be longer 5 chars");
            default -> {
            }
        }
        switch (u.name()) {
            case null -> errors.add("name is null");
            case "" -> errors.add("name is blank");
            case String name && name.length() < 4 -> errors.add("name must be longer 4 chars");
            case String name && !(name.matches(onlyAlphabeticPattern)) ->
                    errors.add("name must contain only alphabetic chars");
            default -> {
            }
        }
        switch (u.surname()) {
            case null -> errors.add("surname is null");
            case "" -> errors.add("surname is blank");
            case String name && name.length() < 4 -> errors.add("surname must be longer 4 chars");
            case String name && !(name.matches(onlyAlphabeticPattern)) ->
                    errors.add("surname must contain only alphabetic chars");
            default -> {
            }
        }
        switch (u.birthdate()) {
            case null -> errors.add("birthdate is null");
            case LocalDate date && LocalDate.now().isBefore(date) -> errors.add("birthdate must be in the past");
            default -> {
            }
        }
        if (errors.size() != 0) {
            throw new RuntimeException(errors.toString());
        }
    }
}
