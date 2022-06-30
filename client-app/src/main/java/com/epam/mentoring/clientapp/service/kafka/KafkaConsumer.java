package com.epam.mentoring.clientapp.service.kafka;

import com.epam.mentoring.common.entity.Notification;
import com.epam.mentoring.clientapp.service.OrderService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.epam.mentoring.common.constant.KafkaConstant.NOTIFICATION_TOPIC;

@Slf4j
@Component
@Data
public class KafkaConsumer {
    private Object payload;

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = NOTIFICATION_TOPIC, groupId = "client-app-group")
    public void receiveNotification(Notification notification) {
        log.info("Receiving Notification message: {}", notification);
        payload = notification;
        var order = orderService.getOrder(notification.getOrderId());
        order.setStatus(notification.getOrderStatus());
        orderService.update(order);
    }

}
