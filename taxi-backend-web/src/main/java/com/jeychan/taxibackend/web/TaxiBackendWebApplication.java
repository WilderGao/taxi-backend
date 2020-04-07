package com.jeychan.taxibackend.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

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
