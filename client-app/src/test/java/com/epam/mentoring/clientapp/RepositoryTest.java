package com.epam.mentoring.clientapp;

import com.epam.mentoring.clientapp.entity.Order;
import com.epam.mentoring.clientapp.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RepositoryTest extends IntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testSaveOrder() {
        var customer = "test";
        var quantity = 2;
        var order = Order.builder()
                .customer(customer)
                .quantity(quantity)
                .build();

        orderRepository.save(order);
        order = orderRepository.findById(1L).orElse(null);

        assertNotNull(order);
        assertEquals(1L, order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(quantity, order.getQuantity());
    }
}
