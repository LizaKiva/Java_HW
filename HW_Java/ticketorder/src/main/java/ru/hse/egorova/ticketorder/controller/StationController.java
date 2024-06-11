package ru.hse.egorova.ticketorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.egorova.ticketorder.model.Station;
import ru.hse.egorova.ticketorder.service.StationService;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {
    @Autowired
    private StationService stationService;

    @PostMapping("/createStation")
    ResponseEntity<String> createStation(
            @RequestParam String name
    ) {
        Station station = stationService.findByName(name);
        if (station != null) {
            return ResponseEntity.status(401).body("Station with that name is already exists");
        }

        stationService.createStation(name);
        return ResponseEntity.ok("Created new station");
    }

    @GetMapping("/listStations")
    ResponseEntity<List<Station>> listStations(
    ) {
        return ResponseEntity.ok(stationService.findAll());
    }
}
