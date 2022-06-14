package com.movies.users.service.repos;

import com.movies.users.service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepo extends JpaRepository<User, UUID> {
}
