package com.example.userService.repositories;

import com.example.userService.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session save(Session session);
}
