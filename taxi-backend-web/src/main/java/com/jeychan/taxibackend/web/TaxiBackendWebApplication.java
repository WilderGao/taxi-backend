package com.jeychan.taxibackend.web;

import com.jeychan.taxibackend.service.interfaces.CsvReadService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

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
