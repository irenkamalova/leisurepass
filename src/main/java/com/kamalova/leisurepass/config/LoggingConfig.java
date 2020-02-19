package com.kamalova.leisurepass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;

@Configuration
public class LoggingConfig {

    @Bean
    public Logbook Logbook() {
        return Logbook.builder()
                .build();
    }
}
