package ru.hse.egorova.ticketorder.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "order_table")
public final class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long fromStationId;

    @Column(nullable = false)
    private Long toStationId;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private LocalDateTime created;
}

