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

    /**
     * Constructs the service with its required repositories.
     *
     * @param orderRepository            repository for persisting {@link Order} entities
     * @param orderProductInfoRepository repository for persisting {@link OrderProductInfo} line items
     * @param productInfoRepository      repository for looking up or creating {@link ProductInfo} snapshots
     */
    @Autowired
    public OrderService(OrderRepository orderRepository, OrderProductInfoRepository orderProductInfoRepository, ProductInfoRepository productInfoRepository) {
        this.orderRepository = orderRepository;
        this.orderProductInfoRepository = orderProductInfoRepository;
        this.productInfoRepository = productInfoRepository;
    }

    /**
     * Persists a new {@link Order} record to the database.
     *
     * @param order the order to save; must not be {@code null}
     */
    public void add(Order order) {
        orderRepository.save(order);
    }

    /**
     * Retrieves a single order by its primary key.
     *
     * @param id the database ID of the order to look up
     * @return an {@link Optional} containing the matching {@link Order}, or empty if not found
     */
    public Optional<Order> get(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Returns a paginated projection of orders belonging to the given user.
     *
     * @param login    the login (username) of the user whose orders are queried
     * @param pageable pagination and sorting parameters
     * @return a {@link Page} of {@link OrderDto} projections for the specified user
     */
    public Page<OrderDto> findDtoByUserLoginPageable(String login, Pageable pageable) {
        return orderRepository.findByUserLogin(login, pageable);
    }

    /**
     * Retrieves all line items associated with a given order.
     *
     * @param orderId the ID of the order whose items should be fetched
     * @return a {@link List} of {@link OrderProductInfo} records for the order
     */
    public List<OrderProductInfo> getOrderItems(Long orderId) {
        return orderProductInfoRepository.findProductInfoByOrderId(orderId);
    }

    /**
     * Creates and persists a new order from the contents of the user's session cart.
     *
     * <p>An {@link Order} with status {@code FORMED} is saved first. For each cart entry a
     * {@link ProductInfo} snapshot is resolved (reusing an existing record when one matches,
     * otherwise creating a new one), and a corresponding {@link OrderProductInfo} line item
     * is persisted. The caller is responsible for clearing the session cart afterwards.</p>
     *
     * @param cart the current session shopping cart containing products and their quantities;
     *             must not be {@code null} or empty
     * @param user the authenticated user placing the order; must not be {@code null}
     */
    public void createOrder(ShoppingCart cart, User user) {
        Order order = new Order(Order.OrderStatus.FORMED, "", Calendar.getInstance(), user);
        orderRepository.save(order);

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            orderProductInfoRepository.save(new OrderProductInfo(entry.getValue(), order, resolveProductInfo(entry.getKey())));
        }
    }

    private ProductInfo resolveProductInfo(Product product) {
        return productInfoRepository
                .findFromProduct(product.getPrice(),
                        product.getName(),
                        product.getCategory(),
                        product.getProducer(),
                        product.getDescription())
                .orElseGet(() -> productInfoRepository.save(new ProductInfo(product)));
    }
}
