package com.springtraining.furnitureshop.entity;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBean {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    @Size(max = 45, message = "too long name")
    private String name;
    private Category category;
    private List<Producer> producers;

    @Min(1)
    private Integer size;

    @Min(1)
    private Integer page;

    private String sortField;

    private Direction sortOrder;

    public boolean isFiltersEmpty() {
        return minPrice == null && maxPrice == null && name == null && category == null && producers == null;
    }

}
