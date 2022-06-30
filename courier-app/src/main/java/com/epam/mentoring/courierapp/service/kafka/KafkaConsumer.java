package com.epam.mentoring.courierapp.service.kafka;

import com.epam.mentoring.common.entity.Notification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.epam.mentoring.common.constant.KafkaConstant.NOTIFICATION_TOPIC;
import static com.epam.mentoring.common.constant.OrderStatus.DELIVERED;
import static com.epam.mentoring.common.constant.OrderStatus.READY;

@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = NOTIFICATION_TOPIC, groupId = "courier-app-group")
    public void receiveNotification(Notification notification) throws InterruptedException {
        if (READY == notification.getOrderStatus()) {
            log.info("Receiving Notification message: {}", notification);
            Thread.sleep(2000);
            notification.setOrderStatus(DELIVERED);
            kafkaProducer.send(NOTIFICATION_TOPIC, String.valueOf(notification.getOrderId()), notification);
        }
    }

}
