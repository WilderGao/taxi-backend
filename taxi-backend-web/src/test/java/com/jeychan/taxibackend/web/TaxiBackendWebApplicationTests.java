package com.jeychan.taxibackend.web;

import com.jeychan.taxibackend.service.interfaces.CsvReadService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TaxiBackendWebApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaxiBackendWebApplicationTests {

    @Autowired
    private CsvReadService csvReadService;

    @Test
    void contextLoads() {
    }

    @Test
    void loadCsvToDatabase() {
        String fileName = "yellow_tripdata_2016-01.xlsx";
        String filePath = "E:\\课内学习\\毕业设计\\收集数据\\";

        csvReadService.importCsvDataToDatabase(fileName, filePath);
    }

}
