package com.epam.mentoring.clientapp.service;

import com.epam.mentoring.clientapp.entity.Order;

public interface OrderService {
    Order placeOrder(Order order);

    Order getOrder(Long id);

    Order update(Order order);
}
