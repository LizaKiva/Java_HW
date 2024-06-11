package ru.hse.egorova.ticketorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.hse.egorova.ticketorder.model.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
}
