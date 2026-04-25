package com.dylansyardsale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Enables component scanning, auto-configuration, and Spring Boot startup behavior.
public class DylansYardSaleApplication { // Main entry class for the application.
    public static void main(String[] args) { // Java entry point that boots the Spring context.
        SpringApplication.run(DylansYardSaleApplication.class, args);
    }
}
