package com.springtraining.furnitureshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestBody {
    @NotNull
    private Long productID;

    @NotNull
    @Min(value = 1)
    @Max(100)
    private Integer quantity;
}
