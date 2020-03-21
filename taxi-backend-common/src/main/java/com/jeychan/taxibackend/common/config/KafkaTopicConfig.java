package com.jeychan.taxibackend.common.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WilderGao
 * time 2020-03-21 20:54
 * motto : everything is no in vain
 * description
 */
@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Value("${spring.kafka.topic.partitions}")
    private int partitions;
    @Value("${spring.kafka.topic.replication-factor}")
    private short replicationFactor;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> properties = new HashMap<>(8);
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(properties);
    }

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(topicName, partitions, replicationFactor);
    }
}
