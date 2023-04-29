package com.springtraining.furnitureshop.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

@Component
@ConfigurationProperties(prefix = "furnitureshop.avatar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarProps {
    private String directory;
    private DataSize maxFileSize;
}
