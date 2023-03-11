package com.springtraining.furnitureshop.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "furnitureshop.products.defaults")
@Data
public class ProductProps {
    private final int page;
    private final int size;
    private final Direction sortOrder;
    private final String sortField;
}
