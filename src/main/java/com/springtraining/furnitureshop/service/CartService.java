package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.util.Attributes;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Service
public class CartService {
    public void add(Product product, HttpSession session) {
        getCart(session).put(product, 1);
    }

    public void remove(Product product, HttpSession session) {
        getCart(session).remove(product);
    }

    public void changeCount(Product product, int count, HttpSession session) {
        ShoppingCart cart = getCart(session);
        if (cart.containsKey(product)) {
            cart.replace(product, count);
        }
    }

    public boolean contains(Product product, HttpSession session) {
        return getCart(session).containsKey(product);
    }

    public int count(HttpSession session) {
        return getCart(session).size();
    }

    public BigDecimal calculateTotal(HttpSession session) {
        return getCart(session).getTotal();
    }

    public ShoppingCart getCart(HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute(Attributes.CART);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            session.setAttribute(Attributes.CART, shoppingCart);
        }
        return shoppingCart;
    }

    public void clear(HttpSession session) {
        getCart(session).clear();
    }
}
