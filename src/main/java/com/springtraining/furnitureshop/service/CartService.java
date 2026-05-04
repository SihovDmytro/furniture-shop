package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.util.Attributes;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Service
public class CartService {
    /**
     * Adds the given product to the session cart with a quantity of 1.
     * If the product is already present its quantity is not changed.
     *
     * @param product the product to add; must not be {@code null}
     * @param session the current HTTP session; must not be {@code null}
     */
    public void add(Product product, HttpSession session) {
        getCart(session).put(product, 1);
    }

    /**
     * Removes the given product from the session cart entirely.
     *
     * @param product the product to remove; must not be {@code null}
     * @param session the current HTTP session; must not be {@code null}
     */
    public void remove(Product product, HttpSession session) {
        getCart(session).remove(product);
    }

    /**
     * Updates the quantity of the given product in the session cart.
     * Does nothing if the product is not already present in the cart.
     *
     * @param product the product whose quantity should be updated; must not be {@code null}
     * @param count   the new quantity
     * @param session the current HTTP session; must not be {@code null}
     */
    public void changeCount(Product product, int count, HttpSession session) {
        ShoppingCart cart = getCart(session);
        if (cart.containsKey(product)) {
            cart.replace(product, count);
        }
    }

    /**
     * Returns {@code true} if the given product is present in the session cart.
     *
     * @param product the product to check; must not be {@code null}
     * @param session the current HTTP session; must not be {@code null}
     * @return {@code true} if the cart contains the product, {@code false} otherwise
     */
    public boolean contains(Product product, HttpSession session) {
        return getCart(session).containsKey(product);
    }

    /**
     * Returns the number of distinct product lines currently in the session cart.
     *
     * @param session the current HTTP session; must not be {@code null}
     * @return the number of unique products in the cart
     */
    public int count(HttpSession session) {
        return getCart(session).size();
    }

    /**
     * Calculates and returns the total price of all items in the session cart.
     *
     * @param session the current HTTP session; must not be {@code null}
     * @return the sum of (price × quantity) for every product line in the cart
     */
    public BigDecimal calculateTotal(HttpSession session) {
        return getCart(session).getTotal();
    }

    /**
     * Returns the {@link ShoppingCart} stored in the session, creating and storing
     * a new empty cart if none exists yet.
     *
     * @param session the current HTTP session; must not be {@code null}
     * @return the session's {@link ShoppingCart}; never {@code null}
     */
    public ShoppingCart getCart(HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute(Attributes.CART);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            session.setAttribute(Attributes.CART, shoppingCart);
        }
        return shoppingCart;
    }

    /**
     * Removes all items from the session cart.
     *
     * @param session the current HTTP session; must not be {@code null}
     */
    public void clear(HttpSession session) {
        getCart(session).clear();
    }
}
