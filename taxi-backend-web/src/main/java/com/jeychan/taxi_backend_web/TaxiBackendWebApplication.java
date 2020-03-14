package com.jeychan.taxi_backend_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Wilder
 */
@SpringBootApplication(scanBasePackages = "com.jeychan.backend")
public class TaxiBackendWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiBackendWebApplication.class, args);
    }

}
