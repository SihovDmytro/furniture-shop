package com.springtraining.furnitureshop.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "furnitureshop.user")
@Data
public class UserProps {
    private final int maxLoginAttempts;
    private final int banDuration;
}
