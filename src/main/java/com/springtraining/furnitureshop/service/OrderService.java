package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.domain.OrderProductInfo;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.domain.ProductInfo;
import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.entity.OrderDto;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.repository.OrderProductInfoRepository;
import com.springtraining.furnitureshop.repository.OrderRepository;
import com.springtraining.furnitureshop.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
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

    public List<OrderProductInfo> getOrderItems(Long orderId) {
        return orderProductInfoRepository.findProductInfoByOrderId(orderId);
    }

    public void createOrder(ShoppingCart cart, User user) {
        Order order = new Order(Order.OrderStatus.FORMED, "", Calendar.getInstance(), user);
        orderRepository.save(order);

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            ProductInfo productInfo = productInfoRepository
                    .findFromProduct(product.getPrice(),
                            product.getName(),
                            product.getCategory(),
                            product.getProducer(),
                            product.getDescription())
                    .orElseGet(() -> productInfoRepository.save(new ProductInfo(product)));

            orderProductInfoRepository.save(new OrderProductInfo(entry.getValue(), order, productInfo));
        }
    }
}
