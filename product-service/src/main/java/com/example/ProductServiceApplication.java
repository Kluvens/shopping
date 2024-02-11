package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ProductServiceApplication {
    public static void main(String[] args) {
        log.info("Starting product service");

        SpringApplication.run(ProductServiceApplication.class, args);
    }
}