package com.example.jdbcAuthentication.h2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * created by Atiye Mousavi
 * Date: 10/2/2021
 * Time: 5:28 PM
 */
@SpringBootApplication
@EnableWebSecurity
@PropertySource("classpath:application-defaults.properties")
public class H2JdbcAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(H2JdbcAuthenticationApplication.class, args);
    }

}
