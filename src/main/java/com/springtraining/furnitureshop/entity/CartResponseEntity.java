package com.springtraining.furnitureshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseEntity {
    private Integer size;
    private BigDecimal cartPrice;
    private BigDecimal total;
}
