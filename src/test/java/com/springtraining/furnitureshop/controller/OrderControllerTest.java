package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.domain.OrderProductInfo;
import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.entity.OrderDto;
import com.springtraining.furnitureshop.entity.OrderSortOption;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.service.CartService;
import com.springtraining.furnitureshop.service.OrderService;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.OrdersProps;
import com.springtraining.furnitureshop.util.PaginationProps;
import com.springtraining.furnitureshop.util.Views;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private static final String TEST_LOGIN = "testLogin";
    private static final long ORDER_ID = 1L;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private CartService cartService;

    private MockMvc mockMvc;
    private Principal principal;

    @BeforeEach
    void setUp() {
        PaginationProps paginationProps = new PaginationProps(List.of("5", "10", "25"), 4);
        OrdersProps ordersProps = new OrdersProps(Sort.Direction.DESC, OrderSortOption.DATE);

        OrderController controller = new OrderController(
                orderService, userService, cartService, paginationProps, ordersProps);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".html"))
                .build();

        principal = () -> TEST_LOGIN;
        ShoppingCart emptyCart = new ShoppingCart();
        lenient().when(cartService.getCart(any())).thenReturn(emptyCart);
    }

    @Test
    void displayOrderDetails_shouldReturn200AndOrdersView() throws Exception {
        Page<OrderDto> page = new PageImpl<>(List.of());
        when(orderService.findDtoByUserLoginPageable(anyString(), any())).thenReturn(page);

        mockMvc.perform(get("/orders").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name(Views.ORDERS))
                .andExpect(model().attributeExists(Attributes.ORDERS));
    }

    @Test
    void createOrder_shouldRedirectToProductsWhenCartIsEmpty() throws Exception {
        mockMvc.perform(post("/orders").principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/" + Views.PRODUCTS + "**"));
    }

    @Test
    void createOrder_shouldRedirectToOrdersAfterSuccessfulCreation() throws Exception {
        ShoppingCart cart = new ShoppingCart();
        cart.put(mock(com.springtraining.furnitureshop.domain.Product.class), 1);
        when(cartService.getCart(any())).thenReturn(cart);

        User user = new User(TEST_LOGIN, "name", "surname", "pass", "email@test.com",
                false, User.Role.USER, 0, null, "");
        when(userService.getUserByLogin(TEST_LOGIN)).thenReturn(Optional.of(user));

        mockMvc.perform(post("/orders").principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/" + Views.ORDERS + "**"));
    }

    @Test
    void createOrder_shouldRedirectToLoginWhenUserNotFound() throws Exception {
        ShoppingCart cart = new ShoppingCart();
        cart.put(mock(com.springtraining.furnitureshop.domain.Product.class), 1);
        when(cartService.getCart(any())).thenReturn(cart);
        when(userService.getUserByLogin(TEST_LOGIN)).thenReturn(Optional.empty());

        mockMvc.perform(post("/orders").principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/" + Views.LOGIN + "**"));
    }

    @Test
    void getProductInfos_shouldReturn200WithOrderItems() throws Exception {
        OrderProductInfo item = new OrderProductInfo(1, null, null);
        when(orderService.getOrderItems(ORDER_ID)).thenReturn(List.of(item));

        mockMvc.perform(get("/orders/{id}", ORDER_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}







