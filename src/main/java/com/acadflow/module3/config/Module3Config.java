package com.acadflow.module3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Configuration for Module 3
 * Sets up beans and configurations for JSON serialization
 */
@Configuration
public class Module3Config {

    /**
     * Configure ObjectMapper for JSON serialization
     * Ensures proper handling of LocalDate and LocalDateTime
     * @return configured ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Register Java 8 date/time module
        mapper.registerModule(new JavaTimeModule());
        // Disable timestamp-based serialization for dates
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
