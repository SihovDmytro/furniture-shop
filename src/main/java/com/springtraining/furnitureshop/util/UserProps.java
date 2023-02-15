package com.springtraining.furnitureshop.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "furnitureshop.user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProps {
    private int maxLoginAttempts;
    private int banDuration;
}
