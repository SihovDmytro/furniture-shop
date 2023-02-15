package com.springtraining.furnitureshop.entity;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBean {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String name;
    private Category category;
    private List<Producer> producers;
    private Integer pageSize;
    private Integer page;
    private String sortField;
    private SortOrder sortOrder;

    public boolean isFiltersEmpty() {
        return minPrice == null && maxPrice == null && name == null && category == null && producers == null;
    }

}
