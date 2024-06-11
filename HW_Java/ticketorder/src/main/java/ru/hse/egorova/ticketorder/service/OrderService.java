package ru.hse.egorova.ticketorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.egorova.ticketorder.model.Order;
import ru.hse.egorova.ticketorder.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StationService stationService;

    public void createOrder(Long userId, Long departure, Long destination) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(1);
        order.setToStationId(departure);
        order.setFromStationId(destination);
        order.setCreated(LocalDateTime.now());

        orderRepository.save(order);
    }

    public List<Order> findAllByUserId(Long userId) {
        List<Order> filtered = new java.util.ArrayList<>(Collections.emptyList());
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if (Objects.equals(order.getUserId(), userId)) {
                filtered.add(order);
            }
        }

        return filtered;
    }

    public List<Order> findAllByStatus(int status) {
        List<Order> filtered = new java.util.ArrayList<>(Collections.emptyList());
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if (Objects.equals(order.getStatus(), status)) {
                filtered.add(order);
            }
        }

        return filtered;
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
