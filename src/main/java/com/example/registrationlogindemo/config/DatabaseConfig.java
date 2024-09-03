package com.example.registrationlogindemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class DatabaseConfig {
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public void checkAndCreateDatabase() {
        try {
            // Attempt to use the database
            jdbcTemplate.execute("USE login_system;");
        } catch (Exception e) {
            // If the database doesn't exist, create it
            jdbcTemplate.execute("CREATE DATABASE login_system;");
            jdbcTemplate.execute("USE login_system;");
        }
    }
	
	
}
