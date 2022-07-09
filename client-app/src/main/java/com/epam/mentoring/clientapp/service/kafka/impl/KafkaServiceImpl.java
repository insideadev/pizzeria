package com.epam.mentoring.clientapp.service.kafka.impl;

import com.epam.mentoring.clientapp.entity.Order;
import com.epam.mentoring.clientapp.service.kafka.KafkaProducer;
import com.epam.mentoring.clientapp.service.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.epam.mentoring.common.constant.KafkaConstant.ORDER_TOPIC;

@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public void sendOrder(Order order) {
        var orderDto = new com.epam.mentoring.common.entity.Order();
        BeanUtils.copyProperties(order, orderDto);
        kafkaProducer.send(ORDER_TOPIC, String.valueOf(order.getId()), orderDto);
    }
}
