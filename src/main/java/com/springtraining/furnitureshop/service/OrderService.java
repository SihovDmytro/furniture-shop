package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.domain.ProductInfo;
import com.springtraining.furnitureshop.entity.OrderDto;
import com.springtraining.furnitureshop.repository.OrderProductInfoRepository;
import com.springtraining.furnitureshop.repository.OrderRepository;
import com.springtraining.furnitureshop.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductInfoRepository orderProductInfoRepository;
    private final ProductInfoRepository productInfoRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderProductInfoRepository orderProductInfoRepository, ProductInfoRepository productInfoRepository) {
        this.orderRepository = orderRepository;
        this.orderProductInfoRepository = orderProductInfoRepository;
        this.productInfoRepository = productInfoRepository;
    }

    public void add(Order order) {
        orderRepository.save(order);
    }

    public Optional<Order> get(Long id) {
        return orderRepository.findById(id);
    }

    public Page<OrderDto> findDtoByUserLoginPageable(String login, Pageable pageable) {
        return orderRepository.findByUserLogin(login, pageable);
    }

    public List<ProductInfo> getOrderItems(Long orderId) {
        return productInfoRepository.findProductInfoByOrderId(orderId);
    }
}
