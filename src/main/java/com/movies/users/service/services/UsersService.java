package com.movies.users.service.services;

import com.movies.users.service.models.User;
import com.movies.users.service.repos.UsersRepo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepo usersRepo;

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @PostConstruct
    private void testUsers() {
        usersRepo.saveAll(List.of(
                new User(UUID.randomUUID(), "username1", "Antonio", "Banderos", "some hash", LocalDate.of(1980, 10, 10)),
                new User(UUID.randomUUID(), "username2", "Lex", "Musk", "some hash", LocalDate.of(1981, 10, 10)),
                new User(UUID.randomUUID(), "username3", "Joe", "Smith", "some hash", LocalDate.of(2000, 10, 10)),
                new User(UUID.randomUUID(), "username4", "Jay", "Brown", "some hash", LocalDate.of(2015, 10, 10)),
                new User(UUID.randomUUID(), "username5", "Alex", "Jones", "some hash", LocalDate.of(2010, 10, 10))));
    }

    public UUID createUser(User u) {
        validate(u);
        var id = UUID.randomUUID();
        usersRepo.save(u);
        return id;
    }

    public User deleteUser(UUID id) {
        var user = findById(id);
        usersRepo.delete(user);
        return user;
    }

    public void updateUser(User u) {
        validate(u);
        usersRepo.save(u);
    }

    public User findUser(User fields) {
        return findByFields(fields);
    }

    public List<User> getAll() {
        return usersRepo.findAll();
    }

    private User findById(UUID id) {
        return usersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    private User findByFields(User u) {
        var users = usersRepo.findAll()
                .stream()
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
        if (u.getId() == null) errors.add("user id is null");
        switch (u.getUsername()) {
            case null -> errors.add("username is null");
            case "" -> errors.add("username is blank");
            case String username && username.length() < 5 -> errors.add("username must be longer 5 chars");
            default -> {
            }
        }
        switch (u.getName()) {
            case null -> errors.add("name is null");
            case "" -> errors.add("name is blank");
            case String name && name.length() < 4 -> errors.add("name must be longer 4 chars");
            case String name && !(name.matches(onlyAlphabeticPattern)) ->
                    errors.add("name must contain only alphabetic chars");
            default -> {
            }
        }
        switch (u.getSurname()) {
            case null -> errors.add("surname is null");
            case "" -> errors.add("surname is blank");
            case String name && name.length() < 4 -> errors.add("surname must be longer 4 chars");
            case String name && !(name.matches(onlyAlphabeticPattern)) ->
                    errors.add("surname must contain only alphabetic chars");
            default -> {
            }
        }
        switch (u.getBirthdate()) {
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
