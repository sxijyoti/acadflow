package com.acadflow.module3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Component scan configuration for Module 3
 * Ensures all Module 3 components are picked up by Spring
 */
@Configuration
@ComponentScan("com.acadflow.module3")
public class Module3ComponentScan {
    // This configuration ensures all Spring components in module3 are scanned and registered
}
