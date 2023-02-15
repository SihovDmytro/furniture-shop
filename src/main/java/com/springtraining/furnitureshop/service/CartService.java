package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void add(Product product) {
        cartRepository.add(product);
    }

    public void changeCount(Product product, int count) {
        cartRepository.changeCount(product, count);
    }

    public void remove(Product product) {
        cartRepository.remove(product);
    }

    public boolean contains(Product product) {
        return cartRepository.contains(product);
    }

    public int count() {
        return cartRepository.count();
    }

    public BigDecimal calculateTotal() {
        return cartRepository.calculateTotal();
    }

    public Map<Product, Integer> getCart() {
        return cartRepository.getCart();
    }

    public void clear() {
        cartRepository.clear();
    }
}
