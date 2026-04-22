package com.springtraining.furnitureshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.CartRequestBody;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.service.CartService;
import com.springtraining.furnitureshop.service.ProductService;
import com.springtraining.furnitureshop.util.Views;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CartService cartService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Product product;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver("/WEB-INF/views/", ".html");
        CartController controller = new CartController(productService, cartService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();

        product = new Product("Chair", new BigDecimal("99.99"),
                new Category("Seating"), new Producer("ACME"), "desc", "img.png");
        ShoppingCart cart = new ShoppingCart();
        cart.put(product, 1);

        lenient().when(cartService.getCart(any())).thenReturn(cart);
    }

    @Test
    void getCart_shouldReturn200AndCartView() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name(Views.CART));
    }

    @Test
    void addProduct_shouldReturn200WithCartSizeWhenProductExists() throws Exception {
        when(productService.getProduct(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(1));
    }

    @Test
    void addProduct_shouldReturn404WhenProductNotFound() throws Exception {
        when(productService.getProduct(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeProduct_shouldReturn200WhenProductExists() throws Exception {
        when(productService.getProduct(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(delete("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1"))
                .andExpect(status().isOk());
    }

    @Test
    void removeProduct_shouldReturn404WhenProductNotFound() throws Exception {
        when(productService.getProduct(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void putProduct_shouldReturn200WhenProductExists() throws Exception {
        when(productService.getProduct(1L)).thenReturn(Optional.of(product));
        CartRequestBody body = new CartRequestBody(1L, 3);

        mockMvc.perform(put("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    void putProduct_shouldReturn404WhenProductNotFound() throws Exception {
        when(productService.getProduct(anyLong())).thenReturn(Optional.empty());
        CartRequestBody body = new CartRequestBody(999L, 2);

        mockMvc.perform(put("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }

    @Test
    void putProduct_shouldReturn404WhenQuantityIsInvalid() throws Exception {
        CartRequestBody body = new CartRequestBody(1L, 0); // quantity < 1

        mockMvc.perform(put("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }
}




