package ru.hse.egorova.authservice.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long userId;

    @Getter
    @Setter
    @Column(nullable = false, length = 255)
    private String token;

    @Getter
    @Setter
    @Column(nullable = false)
    private Date expires;
}

