package com.jeychan.taxibackend.common.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


/**
 * @author WilderGao
 * time 2020-03-21 23:01
 * motto : everything is no in vain
 * description kafka消息发送
 */
@Component
@Slf4j
public class KafkaOperator {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String key, String value) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, value);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("KafkaProducer.sendMessage error: topic={}, key={}, value={}" +
                        ", errorMsg={}", topic, key, value, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> sendResult) {
                log.info("KafkaProducer.sendMessage success: topic={}, key={}, value={}, offset={}",
                        topic, key, value, sendResult.getRecordMetadata().offset());
            }
        });
    }

}
