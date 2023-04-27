package com.springtraining.furnitureshop.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "furnitureshop.products")
@Data
public class ProductPageProps {
    private final List<String> pageSizes;
    private final int paginationRange;
}
