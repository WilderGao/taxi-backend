package com.jeychan.taxibackend.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Wilder
 */
@SpringBootApplication(scanBasePackages = "com.jeychan.taxibackend")
@MapperScan(basePackages = "com.jeychan.taxibackend.dao.mapper")
public class TaxiBackendWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiBackendWebApplication.class, args);
    }

}
