package ru.hse.egorova.ticketorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.egorova.ticketorder.model.Station;
import ru.hse.egorova.ticketorder.repository.StationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StationService {
    @Autowired
    private StationRepository stationRepository;

    public Station findByName(String stationName) {
        List<Station> stations = stationRepository.findAll();
        for (Station station : stations) {
            if (Objects.equals(station.getName(), stationName)) {
                return station;
            }
        }

        return null;
    }

    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    public void createStation(String stationName) {
        Station station = new Station();
        station.setName(stationName);
        stationRepository.save(station);
    }
}
