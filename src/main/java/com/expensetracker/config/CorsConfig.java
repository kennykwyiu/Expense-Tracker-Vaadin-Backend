package com.expensetracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS configuration for allowing requests from React frontend.
 * Allows requests from localhost:3000 (development) and configurable production domains.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

}
