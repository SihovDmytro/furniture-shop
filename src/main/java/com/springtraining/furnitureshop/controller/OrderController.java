package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.domain.ProductInfo;
import com.springtraining.furnitureshop.entity.OrderDto;
import com.springtraining.furnitureshop.entity.OrderSortOption;
import com.springtraining.furnitureshop.entity.OrdersBean;
import com.springtraining.furnitureshop.service.OrderService;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.OrdersProps;
import com.springtraining.furnitureshop.util.PaginationProps;
import com.springtraining.furnitureshop.util.Parameters;
import com.springtraining.furnitureshop.util.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final PaginationProps paginationProps;
    private final OrdersProps ordersProps;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, PaginationProps paginationProps, OrdersProps ordersProps) {
        this.orderService = orderService;
        this.userService = userService;
        this.paginationProps = paginationProps;
        this.ordersProps = ordersProps;
    }

    @ModelAttribute(name = "pageProperties")
    public PaginationProps pageProperties() {
        return paginationProps;
    }

    @ModelAttribute(name = "sortFields")
    public List<OrderSortOption> sortFields() {
        return Arrays.asList(OrderSortOption.values());
    }

    @ModelAttribute(name = "sortOrders")
    public List<Sort.Direction> sortOrders() {
        return Arrays.asList(Sort.Direction.values());
    }

    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ProductInfo>> getProductInfos(@PathVariable Long id) {
        log.trace("getProductInfos start");
        log.info(Constants.LOGGER_FORMAT, Parameters.ID, id);
        List<ProductInfo> orderItems = orderService.getOrderItems(id);
        log.info(Constants.LOGGER_FORMAT, Attributes.ORDER_ITEMS, orderItems);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping
    public String displayOrderDetails(@Valid OrdersBean ordersBean, Principal principal, Model model) {
        log.trace("displayOrderDetails start");
        log.info(Constants.LOGGER_FORMAT, "principal", principal.getName());
        setRequiredProperties(ordersBean);
        log.info(Constants.LOGGER_FORMAT, Attributes.ORDERS_BEAN, ordersBean);
        Pageable pageable = getPage(ordersBean);
        log.info(Constants.LOGGER_FORMAT, "pageable", pageable);
        Page<OrderDto> orders = orderService.findDtoByUserLoginPageable(principal.getName(), pageable);
        log.info(Constants.LOGGER_FORMAT,
                Attributes.ORDERS,
                orders.getContent().stream()
                        .map(OrderDto::getString)
                        .collect(Collectors.joining("\n")));
        model.addAttribute(Attributes.ORDERS, orders);
        return Views.ORDERS;
    }

    private Pageable getPage(OrdersBean bean) {
        int size = Integer.MAX_VALUE;
        int currentPage = 1;
        currentPage--;
        Sort.Direction direction = bean.getSortOrder() == null ? ordersProps.getSortOrder() : bean.getSortOrder();
        OrderSortOption sortField = bean.getSortField() == null ? ordersProps.getSortField() : bean.getSortField();
        return PageRequest.of(
                currentPage,
                size, JpaSort.unsafe(direction, "(" + sortField.toString().toLowerCase() + ")"));
    }

    private void setRequiredProperties(OrdersBean ordersBean) {
        if (ordersBean.getSortField() == null) {
            ordersBean.setSortField(ordersProps.getSortField());
        }
        if (ordersBean.getSortOrder() == null) {
            ordersBean.setSortOrder(ordersProps.getSortOrder());
        }
    }
}
