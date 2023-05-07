package com.springtraining.furnitureshop.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "furnitureshop.pagination")
public class PaginationProps {
    private List<String> pageSizes;
    private int paginationRange;
}
