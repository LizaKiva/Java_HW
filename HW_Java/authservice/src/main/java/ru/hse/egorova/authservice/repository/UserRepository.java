package ru.hse.egorova.authservice.repository;

import ru.hse.egorova.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
