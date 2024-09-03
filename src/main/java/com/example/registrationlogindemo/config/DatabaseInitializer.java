package com.example.registrationlogindemo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseInitializer {

	
	@Bean
    public CommandLineRunner initializeDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                // Attempt to select the database
                jdbcTemplate.execute("USE login_system;");
            } catch (Exception e) {
                // If the database doesn't exist, create it
                jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS login_system;");
                jdbcTemplate.execute("USE login_system;");
            }
        };
    }
}
