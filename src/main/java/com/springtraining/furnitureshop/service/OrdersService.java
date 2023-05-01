package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdersService {
    private final OrderRepository orderRepository;

    public OrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void add(Order order) {
        orderRepository.save(order);
    }
}
