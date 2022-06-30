package com.epam.mentoring.clientapp.controller;

import com.epam.mentoring.clientapp.entity.Order;
import com.epam.mentoring.clientapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.placeOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderInfo(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

}
