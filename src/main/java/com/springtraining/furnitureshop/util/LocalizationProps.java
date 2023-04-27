package com.springtraining.furnitureshop.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "furnitureshop.localization")
public class LocalizationProps {
    private final String defaultLocale;
    private final int cookieMaxAge;
}
