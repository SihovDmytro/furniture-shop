package com.springtraining.furnitureshop.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "furnitureshop.products.defaults")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductProps {
    private int page;
    private int size;
    private Direction sortOrder;
    private String sortField;
}
