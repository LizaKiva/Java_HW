package ru.hse.egorova.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.hse.egorova.authservice.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
