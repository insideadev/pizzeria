package com.epam.mentoring.clientapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.epam.mentoring.common.constant.KafkaConstant.*;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(ORDER_TOPIC)
                .partitions(PARTITIONS_NUMBER)
                .build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name(NOTIFICATION_TOPIC)
                .partitions(PARTITIONS_NUMBER)
                .build();
    }
}
