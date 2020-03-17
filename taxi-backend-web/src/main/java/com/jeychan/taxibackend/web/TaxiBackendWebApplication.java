package com.jeychan.taxibackend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Wilder
 */
@SpringBootApplication(scanBasePackages = "com.jeychan.taxibackend.common")
public class TaxiBackendWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiBackendWebApplication.class, args);
    }

}
