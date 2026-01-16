package com.example.users_api.service;

import com.example.users_api.dto.OrderCreateRequest;
import com.example.users_api.exception.UserNotFoundException;
import com.example.users_api.model.Order;
import com.example.users_api.model.User;
import com.example.users_api.repository.OrderRepository;
import com.example.users_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order createForUser(Long userId, OrderCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Order order = new Order();
        order.setTotal(request.getTotal());
        order.setUser(user);

        return orderRepository.save(order);
    }

    public List<Order> getByUser(Long userId) {
        // valida que el user existe para devolver 404 (opcional pero pro)
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return orderRepository.findByUserId(userId);
    }
}
