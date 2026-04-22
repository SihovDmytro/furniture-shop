package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.util.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    private CartService cartService;
    private HttpSession session;
    private ShoppingCart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
        session = mock(HttpSession.class);
        cart = new ShoppingCart();
        product = new Product("Chair", new BigDecimal("99.99"),
                new Category("Seating"), new Producer("ACME"), "Nice chair", "img.png");

        lenient().when(session.getAttribute(Attributes.CART)).thenReturn(cart);
    }

    @Test
    void getCart_shouldCreateNewCartWhenSessionHasNone() {
        HttpSession emptySession = mock(HttpSession.class);
        when(emptySession.getAttribute(Attributes.CART)).thenReturn(null);
        ArgumentCaptor<ShoppingCart> captor = ArgumentCaptor.forClass(ShoppingCart.class);

        ShoppingCart result = cartService.getCart(emptySession);

        assertNotNull(result);
        verify(emptySession).setAttribute(eq(Attributes.CART), captor.capture());
        assertEquals(result, captor.getValue());
    }

    @Test
    void getCart_shouldReturnExistingCartFromSession() {
        ShoppingCart result = cartService.getCart(session);

        assertEquals(cart, result);
    }

    @Test
    void add_shouldPutProductWithQuantityOne() {
        cartService.add(product, session);

        assertEquals(1, cart.size());
        assertEquals(1, cart.get(product));
    }

    @Test
    void remove_shouldRemoveProductFromCart() {
        cart.put(product, 3);

        cartService.remove(product, session);

        assertEquals(0, cart.size());
    }

    @Test
    void changeCount_shouldUpdateQuantityIfProductPresent() {
        cart.put(product, 1);

        cartService.changeCount(product, 5, session);

        assertEquals(5, cart.get(product));
    }

    @Test
    void changeCount_shouldDoNothingIfProductNotPresent() {
        cartService.changeCount(product, 5, session);

        assertFalse(cart.containsKey(product));
    }

    @Test
    void contains_shouldReturnTrueAfterAdd() {
        cart.put(product, 1);

        assertTrue(cartService.contains(product, session));
    }

    @Test
    void contains_shouldReturnFalseForEmptyCart() {
        assertFalse(cartService.contains(product, session));
    }

    @Test
    void count_shouldReturnNumberOfDistinctProducts() {
        cart.put(product, 2);
        Product other = new Product("Desk", new BigDecimal("199.00"),
                new Category("Tables"), new Producer("ACME"), "Desk", "desk.png");
        cart.put(other, 1);

        assertEquals(2, cartService.count(session));
    }

    @Test
    void calculateTotal_shouldReturnSumOfPricesTimesQuantities() {
        cart.put(product, 2); // 2 * 99.99 = 199.98

        BigDecimal total = cartService.calculateTotal(session);

        assertEquals(new BigDecimal("199.98"), total);
    }

    @Test
    void clear_shouldEmptyTheCart() {
        cart.put(product, 1);

        cartService.clear(session);

        assertEquals(0, cart.size());
    }
}



