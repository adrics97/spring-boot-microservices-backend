package com.example.orders_service.controller;

import com.example.orders_service.model.Order;
import com.example.orders_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/users/{userId}")
    public Order create(
            @PathVariable Long userId,
            @RequestBody BigDecimal total
    ) {
        return service.create(userId, total);
    }

    @GetMapping("/users/{userId}")
    public List<Order> byUser(@PathVariable Long userId) {
        return service.findByUser(userId);
    }
}
