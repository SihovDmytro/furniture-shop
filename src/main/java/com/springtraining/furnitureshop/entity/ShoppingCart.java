package com.springtraining.furnitureshop.entity;

import com.springtraining.furnitureshop.domain.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class ShoppingCart implements Map<Product, Integer> {
    private Map<Product, Integer> items = new HashMap<>();

    public BigDecimal getTotal() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            BigDecimal price = entry.getKey().getPrice();
            BigDecimal number = new BigDecimal(entry.getValue());
            totalPrice = totalPrice.add(price.multiply(number));
        }
        return totalPrice;
    }

    public BigDecimal getTotalForProduct(Product product) {
        BigDecimal total = new BigDecimal("0");
        if (items.containsKey(product)) {
            total = product.getPrice().multiply(BigDecimal.valueOf(items.get(product)));
        }
        return total;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return items.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return items.containsValue(value);
    }

    @Override
    public Integer get(Object key) {
        return items.get(key);
    }

    @Override
    public Integer put(Product key, Integer value) {
        return items.put(key, value);
    }

    @Override
    public Integer remove(Object key) {
        return items.remove(key);
    }

    @Override
    public void putAll(Map<? extends Product, ? extends Integer> m) {
        items.putAll(m);
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public Set<Product> keySet() {
        return items.keySet();
    }

    @Override
    public Collection<Integer> values() {
        return items.values();
    }

    @Override
    public Set<Entry<Product, Integer>> entrySet() {
        return items.entrySet();
    }

}
