package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.entity.OrderDto;
import com.springtraining.furnitureshop.entity.OrderSortOption;
import com.springtraining.furnitureshop.entity.OrdersBean;
import com.springtraining.furnitureshop.service.OrderService;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @ModelAttribute(name = "sortFields")
    public List<OrderSortOption> sortFields() {
        return Arrays.asList(OrderSortOption.values());
    }

    @ModelAttribute(name = "sortOrders")
    public List<Sort.Direction> sortOrders() {
        return Arrays.asList(Sort.Direction.values());
    }

    @GetMapping
    public String displayOrderDetails(OrdersBean ordersBean, Principal principal, Model model) {
        log.trace("displayOrderDetails start");
        log.info(Constants.LOGGER_FORMAT, "principal", principal.getName());
        log.info(Constants.LOGGER_FORMAT, Attributes.ORDERS_BEAN, ordersBean);

        Sort sort = Sort.by(ordersBean.getSortOrder(), ordersBean.getSortField().toString());
        List<OrderDto> orders = orderService.findDtoByUserLogin(principal.getName(), sort);
        log.info(Constants.LOGGER_FORMAT, Attributes.ORDERS, orders.size());
        model.addAttribute(Attributes.ORDERS, orders);


        return Views.ORDERS;
    }

}
