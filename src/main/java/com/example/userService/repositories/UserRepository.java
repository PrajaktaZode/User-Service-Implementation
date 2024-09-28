package com.example.userService.repositories;

import com.example.userService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//? Why interface and extends.
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    Optional<User> findByEmail(String email);
}
