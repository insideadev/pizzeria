package com.epam.mentoring.clientapp.service.impl;

import com.epam.mentoring.clientapp.entity.Order;
import com.epam.mentoring.clientapp.repository.OrderRepository;
import com.epam.mentoring.clientapp.service.OrderService;
import com.epam.mentoring.clientapp.service.kafka.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static com.epam.mentoring.common.constant.OrderStatus.CREATED;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private KafkaService kafkaService;

    @Override
    public Order placeOrder(Order order) {
        order.setStatus(CREATED);
        var now = LocalDateTime.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        orderRepository.save(order);
        kafkaService.sendOrder(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

}
