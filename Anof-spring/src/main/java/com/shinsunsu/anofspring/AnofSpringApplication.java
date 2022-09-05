package com.shinsunsu.anofspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AnofSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnofSpringApplication.class, args);
    }

}
