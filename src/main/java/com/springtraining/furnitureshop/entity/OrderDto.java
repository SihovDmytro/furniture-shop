package com.springtraining.furnitureshop.entity;

import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.util.DateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface OrderDto {
    Long getId();

    BigDecimal getPrice();

    int getItems();

    Order.OrderStatus getStatus();

    String getStatusDescription();

    Timestamp getDate();

    default String getString() {
        return String.format("OrderDto{id=%d, price=%s, items=%d, status=%s, statusDescription='%s', date=%s}",
                getId(),
                getPrice().toPlainString(),
                getItems(),
                getStatus(),
                getStatusDescription(),
                DateUtil.timestampToString(getDate()));
    }
}
