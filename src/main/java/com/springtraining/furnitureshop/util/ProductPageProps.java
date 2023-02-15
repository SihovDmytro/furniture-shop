package com.springtraining.furnitureshop.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "furnitureshop.products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageProps {
    private List<String> pageSizes;
    private int productsPerLine;
    private int paginationRange;
}
