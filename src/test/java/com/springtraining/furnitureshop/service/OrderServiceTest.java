package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.domain.OrderProductInfo;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.domain.ProductInfo;
import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.entity.OrderDto;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.repository.OrderProductInfoRepository;
import com.springtraining.furnitureshop.repository.OrderRepository;
import com.springtraining.furnitureshop.repository.ProductInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final long ORDER_ID = 42L;
    private static final String TEST_LOGIN = "testLogin";

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductInfoRepository orderProductInfoRepository;

    @Mock
    private ProductInfoRepository productInfoRepository;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User(TEST_LOGIN, "name", "surname", "password", "email@test.com",
                false, User.Role.USER, 0, null, "");
        product = new Product("Chair", new BigDecimal("50.00"),
                new Category("Seating"), new Producer("ACME"), "desc", "img.png");
    }

    @Test
    void add_shouldDelegateToRepository() {
        Order order = new Order(Order.OrderStatus.FORMED, "", null, user);

        orderService.add(order);

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void get_shouldReturnOrderFromRepository() {
        Order order = new Order(Order.OrderStatus.FORMED, "", null, user);
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.get(ORDER_ID);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
        verify(orderRepository, times(1)).findById(ORDER_ID);
    }

    @Test
    void findDtoByUserLoginPageable_shouldDelegateToRepository() {
        Pageable pageable = PageRequest.of(0, 10);
        OrderDto dto = mock(OrderDto.class);
        Page<OrderDto> page = new PageImpl<>(List.of(dto));
        when(orderRepository.findByUserLogin(TEST_LOGIN, pageable)).thenReturn(page);

        Page<OrderDto> result = orderService.findDtoByUserLoginPageable(TEST_LOGIN, pageable);

        assertEquals(1, result.getContent().size());
        verify(orderRepository, times(1)).findByUserLogin(TEST_LOGIN, pageable);
    }

    @Test
    void getOrderItems_shouldDelegateToRepository() {
        OrderProductInfo item = mock(OrderProductInfo.class);
        when(orderProductInfoRepository.findProductInfoByOrderId(ORDER_ID)).thenReturn(List.of(item));

        List<OrderProductInfo> result = orderService.getOrderItems(ORDER_ID);

        assertEquals(1, result.size());
        verify(orderProductInfoRepository, times(1)).findProductInfoByOrderId(ORDER_ID);
    }

    @Test
    void createOrder_shouldPersistOrderAndLineItems() {
        ShoppingCart cart = new ShoppingCart();
        cart.put(product, 2);

        ProductInfo productInfo = mock(ProductInfo.class);
        when(productInfoRepository.findFromProduct(
                product.getPrice(), product.getName(), product.getCategory(),
                product.getProducer(), product.getDescription()))
                .thenReturn(Optional.of(productInfo));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        orderService.createOrder(cart, user);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderProductInfoRepository, times(1)).save(any(OrderProductInfo.class));
        // productInfo already existed, so productInfoRepository.save should NOT be called
        verify(productInfoRepository, times(0)).save(any(ProductInfo.class));
    }

    @Test
    void createOrder_shouldCreateNewProductInfoSnapshotWhenNoneExists() {
        ShoppingCart cart = new ShoppingCart();
        cart.put(product, 1);

        ProductInfo newInfo = mock(ProductInfo.class);
        when(productInfoRepository.findFromProduct(
                product.getPrice(), product.getName(), product.getCategory(),
                product.getProducer(), product.getDescription()))
                .thenReturn(Optional.empty());
        when(productInfoRepository.save(any(ProductInfo.class))).thenReturn(newInfo);
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        orderService.createOrder(cart, user);

        verify(productInfoRepository, times(1)).save(any(ProductInfo.class));
        verify(orderProductInfoRepository, times(1)).save(any(OrderProductInfo.class));
    }

    @Test
    void createOrder_withMultipleItems_shouldSaveOneLineItemPerProduct() {
        Product product2 = new Product("Desk", new BigDecimal("200.00"),
                new Category("Tables"), new Producer("ACME"), "desk desc", "desk.png");
        ShoppingCart cart = new ShoppingCart();
        cart.put(product, 1);
        cart.put(product2, 3);

        ProductInfo info = mock(ProductInfo.class);
        when(productInfoRepository.findFromProduct(any(), any(), any(), any(), any()))
                .thenReturn(Optional.of(info));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        orderService.createOrder(cart, user);

        verify(orderProductInfoRepository, times(2)).save(any(OrderProductInfo.class));
    }
}


