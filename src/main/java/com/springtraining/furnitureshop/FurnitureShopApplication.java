package com.springtraining.furnitureshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableConfigurationProperties
@EnableJpaRepositories
@SpringBootApplication
public class FurnitureShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurnitureShopApplication.class, args);
    }

}
