package com.epam.mentoring.clientapp.service.kafka;

import com.epam.mentoring.clientapp.entity.Order;

public interface KafkaService {
    void sendOrder(Order order);
}
