package ru.hse.egorova.ticketorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.egorova.ticketorder.client.AuthClient;
import ru.hse.egorova.ticketorder.model.Order;
import ru.hse.egorova.ticketorder.model.Station;
import ru.hse.egorova.ticketorder.service.OrderService;
import ru.hse.egorova.ticketorder.service.StationService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class OrderController {
    @Autowired
    private StationService stationService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AuthClient authClient;

    @PostMapping("/createOrder")
    ResponseEntity<String> createOrder(
            @RequestParam String token,
            @RequestParam String departure,
            @RequestParam String destination
    ) {
        Long userId = authClient.getIdImpl(token);
        if (userId == -1) {
            return ResponseEntity.status(403).body("Failed to auth with token");
        }
        Station departureStation = stationService.findByName(departure);
        Station destinationStation = stationService.findByName(destination);

        if (departureStation == null) {
            return ResponseEntity.status(401).body("Departure station is invalid");
        }
        if (destinationStation == null) {
            return ResponseEntity.status(401).body("Destination station is invalid");
        }

        if (departureStation == destinationStation) {
            return ResponseEntity.status(402).body("Departure and destination can't be the same");
        }

        orderService.createOrder(userId, departureStation.getId(), destinationStation.getId());
        return ResponseEntity.ok("Created new order");
    }

    @GetMapping("/getUserOrders")
    ResponseEntity<?> getUserOrders(
            @RequestParam String token
    ) {
        Long userId = authClient.getIdImpl(token);
        if (userId == -1) {
            return ResponseEntity.status(403).body("Failed to auth with token");
        }

        return ResponseEntity.ok(orderService.findAllByUserId(userId));
    }

    @GetMapping("/getUserOrderStatus")
    ResponseEntity<String> getUserOrderStatus(
            @RequestParam String token
    ) {
        Long userId = authClient.getIdImpl(token);
        if (userId == -1) {
            return ResponseEntity.status(403).body("Failed to auth with token");
        }

        List<Order> orders = orderService.findAllByUserId(userId);
        int unprocessed = 0;
        int accepted = 0;
        int declined = 0;
        int errors = 0;

        for (Order order : orders) {
            if (order.getStatus() == 1) {
                ++unprocessed;
            } else if (order.getStatus() == 2) {
                ++accepted;
            } else if (order.getStatus() == 3) {
                ++declined;
            } else {
                ++errors;
            }
        }

        return ResponseEntity.ok(String.format("unprocessed: %d\naccepted: %d\ndeclined: %d\nerrors: %d", unprocessed, accepted, declined, errors));
    }

    @GetMapping("/getOrderStatus")
    ResponseEntity<String> getOrderStatus(
            @RequestParam Long id
    ) {
        Order order = orderService.findById(id);
        if (order == null) {
            return ResponseEntity.status(401).body("No order with that id");
        }

        if (order.getStatus() == 1) {
            return ResponseEntity.ok("Processing");
        } else if (order.getStatus() == 2) {
            return ResponseEntity.ok("Confirmed");
        } else if (order.getStatus() == 3) {
            return ResponseEntity.ok("Declined");
        } else {
            return ResponseEntity.status(403).body("Unknown status");
        }
    }
}
