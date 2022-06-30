package com.epam.mentoring.common.entity;

import com.epam.mentoring.common.constant.OrderStatus;
import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private Long id;
    private Integer quantity;
    private String customer;
    private OrderStatus status;
}
