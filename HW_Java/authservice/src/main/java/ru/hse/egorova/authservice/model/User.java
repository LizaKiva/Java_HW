package ru.hse.egorova.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_table")
public class User {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false, length = 50)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false, length = 255)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime created;
}

