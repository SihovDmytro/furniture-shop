package com.springtraining.furnitureshop.util;

import com.springtraining.furnitureshop.entity.OrderSortOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "furnitureshop.orders.defaults")
public class OrdersProps {
    private Direction sortOrder;
    private OrderSortOption sortField;
}
