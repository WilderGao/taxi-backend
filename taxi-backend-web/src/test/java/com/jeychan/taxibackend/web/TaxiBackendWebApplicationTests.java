package com.jeychan.taxibackend.web;

import com.jeychan.taxibackend.service.service.ExcelReadService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TaxiBackendWebApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaxiBackendWebApplicationTests {

    @Autowired
    private ExcelReadService excelReadService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void contextLoads() {
        kafkaTemplate.send("gjc.trip.pickup", "aaaaaa");
    }

    @Test
    public void loadCsvToDatabase() {
        String fileName = "yellow_tripdata_2016-01 - 副本.csv";
        String filePath = "E:\\课内学习\\毕业设计\\收集数据\\";

        excelReadService.importCsvDataToDatabase(fileName, filePath);
    }

}
