package com.springtraining.furnitureshop.util;

import com.springtraining.furnitureshop.entity.SortOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "furnitureshop.products.defaults")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductProps {
    private int page;
    private int pageSize;
    private SortOrder sortOrder;
    private String sortField;
}
