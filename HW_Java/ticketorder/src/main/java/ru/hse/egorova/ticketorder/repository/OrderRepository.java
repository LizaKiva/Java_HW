package ru.hse.egorova.ticketorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.hse.egorova.ticketorder.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
