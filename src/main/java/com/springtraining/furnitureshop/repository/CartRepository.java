package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.Product;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Map;

@Repository
public interface CartRepository {
    void add(Product product);

    void remove(Product product);

    void changeCount(Product product, int count);

    boolean contains(Product product);

    int count();

    BigDecimal calculateTotal();

    Map<Product, Integer> getCart();

    void clear();
}
