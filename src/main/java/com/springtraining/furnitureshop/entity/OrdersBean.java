package com.springtraining.furnitureshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersBean {
    private OrderSortOption sortField;

    private Sort.Direction sortOrder;
}
