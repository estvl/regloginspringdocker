package com.example.registrationlogindemo.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JpaConfig {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ConfigurableEnvironment env;

    @PostConstruct
    public void initialize() {
        checkAndCreateDatabase();
        configureJpaProperties();
    }

    public void checkAndCreateDatabase() {
        try {
            // Attempt to select the database
            jdbcTemplate.execute("USE login_system;");
        } catch (Exception e) {
            // If the database doesn't exist, create it
            jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS login_system;");
            jdbcTemplate.execute("USE login_system;");
        }
    }

    public void configureJpaProperties() {
        String currentDdlAuto = determineDdlAutoValue();
        env.getSystemProperties().put("spring.jpa.hibernate.ddl-auto", currentDdlAuto);
    }

    private String determineDdlAutoValue() {
        try {
            // Check if a key table exists in the database
            jdbcTemplate.queryForObject("SELECT 1 FROM users LIMIT 1", Integer.class);
            return "update";  // If the table exists, use 'update'
        } catch (Exception e) {
            return "create";  // If the table doesn't exist, use 'create'
        }
    }
}
