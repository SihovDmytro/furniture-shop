package com.springtraining.furnitureshop.entity;

import com.springtraining.furnitureshop.domain.Order;

import java.math.BigDecimal;
import java.util.Calendar;

public interface OrderDto {
    BigDecimal getTotalPrice();

    int getItems();

    Order.OrderStatus getStatus();

    String getStatusDescription();

    Calendar getDate();
}
