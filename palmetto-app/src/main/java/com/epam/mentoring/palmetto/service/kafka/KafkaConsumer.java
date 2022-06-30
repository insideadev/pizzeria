package com.epam.mentoring.palmetto.service.kafka;

import com.epam.mentoring.common.entity.Notification;
import com.epam.mentoring.common.entity.Order;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.epam.mentoring.common.constant.KafkaConstant.NOTIFICATION_TOPIC;
import static com.epam.mentoring.common.constant.KafkaConstant.ORDER_TOPIC;
import static com.epam.mentoring.common.constant.OrderStatus.CREATED;
import static com.epam.mentoring.common.constant.OrderStatus.READY;

@Slf4j
@Component
@Data
public class KafkaConsumer {

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = ORDER_TOPIC, groupId = "palmetto-app-group")
    public void receiveOrder(Order order) throws InterruptedException {
        log.info("Receiving Order message: {}", order);
        if (CREATED == order.getStatus()) {
            Thread.sleep(2000);
            var notification = Notification.builder()
                    .orderId(order.getId())
                    .orderStatus(READY)
                    .build();
            kafkaProducer.send(NOTIFICATION_TOPIC, String.valueOf(notification.getOrderId()), notification);
        }
    }

}
