package com.example.orders_service.service;

import com.example.orders_service.client.UsersClient;
import com.example.orders_service.exception.UserNotFoundException;
import com.example.orders_service.model.Order;
import com.example.orders_service.repository.OrderRepository;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    private final UsersClient usersClient;

    public OrderService(OrderRepository repository, UsersClient usersClient) {
        this.repository = repository;
        this.usersClient = usersClient;
    }

    public Order create(Long userId, BigDecimal total) {

        if (!usersClient.userExists(userId)) {
            throw new UserNotFoundException(userId);
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(total);
        return repository.save(order);
    }

    public List<Order> findByUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
