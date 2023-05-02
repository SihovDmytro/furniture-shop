package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.entity.OrderDto;
import com.springtraining.furnitureshop.repository.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void add(Order order) {
        orderRepository.save(order);
    }

    public Optional<Order> get(Long id) {
        return orderRepository.findById(id);
    }

    public List<OrderDto> findDtoByUserLogin(String login, Sort sort) {
        return orderRepository.findDtoByUserLogin(login, sort);
    }

}
