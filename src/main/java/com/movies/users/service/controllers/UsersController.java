package com.movies.users.service.controllers;

import com.movies.users.service.models.User;
import com.movies.users.service.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public UUID newUser(@RequestBody User u) {
        return usersService.createUser(u);
    }

    @GetMapping
    public List<User> allUsers() {
        return usersService.getAll();
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable UUID id) {
        return usersService.deleteUser(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser( @RequestBody User u) {
        usersService.updateUser(u);
    }

    @GetMapping("/find")
    public User findUser(@RequestBody User u) {
        return usersService.findUser(u);
    }
}
