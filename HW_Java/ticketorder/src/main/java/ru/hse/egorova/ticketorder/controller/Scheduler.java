package ru.hse.egorova.ticketorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ru.hse.egorova.ticketorder.model.Order;
import ru.hse.egorova.ticketorder.repository.OrderRepository;
import ru.hse.egorova.ticketorder.service.OrderService;

import java.util.List;

@Configuration
@EnableScheduling
public class Scheduler {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @Scheduled(fixedRate = 1000)
    public void processOrders() throws InterruptedException {
        List<Order> openOrders = orderService.findAllByStatus(1);
        for (Order order : openOrders) {
            Thread.sleep(1000);

            if (Math.random() <= 0.5) {
                order.setStatus(3);
            } else {
                order.setStatus(2);
            }

            orderRepository.save(order);
        }
    }
}
