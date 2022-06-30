package com.epam.mentoring.palmetto.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String key, Object value) {
        kafkaTemplate.send(topic, key, value)
                .completable()
                .whenComplete((result, t) -> {
                    if (result != null) {
                        var metadata = result.getRecordMetadata();
                        log.info("Successfully sent to topic: {}, partition:{}, offset:{}, message: {}", topic, metadata.partition(), metadata.offset(), value);
                    } else {
                        log.error("Error when sending message to topic: {}", topic);
                    }
                });
    }
}
