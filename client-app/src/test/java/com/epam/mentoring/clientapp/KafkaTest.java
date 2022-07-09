package com.epam.mentoring.clientapp;

import com.epam.mentoring.common.entity.Notification;
import com.epam.mentoring.clientapp.service.kafka.KafkaConsumer;
import com.epam.mentoring.clientapp.service.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.epam.mentoring.common.constant.KafkaConstant.NOTIFICATION_TOPIC;
import static com.epam.mentoring.common.constant.OrderStatus.READY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class KafkaTest extends IntegrationTest {

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumer consumer;

    @Test
    public void givenKafkaDockerContainer_whenSendingWithSimpleProducer_thenMessageReceived() throws InterruptedException {
        var orderId = 1L;
        var orderStatus = READY;
        var data = Notification.builder()
                .orderId(orderId)
                .orderStatus(orderStatus)
                .build();
        Thread.sleep(2000); // wait for the consumer to join the group

        producer.send(NOTIFICATION_TOPIC, "1", data);
        Thread.sleep(1000);

        var payload = (Notification) consumer.getPayload();
        assertNotNull(payload);
        assertEquals(payload.getOrderId(), orderId);
        assertEquals(payload.getOrderStatus(), orderStatus);
    }

}
